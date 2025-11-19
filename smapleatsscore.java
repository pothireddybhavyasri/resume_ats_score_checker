import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class smapleatsscore extends JFrame implements ActionListener {

    JTextArea resumeArea, jdArea, resultArea;
    JButton scanBtn, clearBtn;

    public smapleatsscore() {

        setTitle(" Simple Resume ATS Scanner");
        setSize(700, 500);
        setLayout(new BorderLayout());

        // Header
        JLabel head = new JLabel("Resume ATS Scanner", JLabel.CENTER);
        head.setOpaque(true);
        head.setBackground(new Color(30, 144, 255));
        head.setForeground(Color.white);
        head.setFont(new Font("Arial", Font.BOLD, 24));
        add(head, BorderLayout.NORTH);

        // Panels
        JPanel center = new JPanel(new GridLayout(1, 2, 10, 10));
        JPanel left = new JPanel(new BorderLayout());
        JPanel right = new JPanel(new BorderLayout());

        // Resume Area
        left.setBorder(BorderFactory.createTitledBorder("Paste Resume Text"));
        resumeArea = new JTextArea();
        left.add(new JScrollPane(resumeArea));

        // JD Area
        right.setBorder(BorderFactory.createTitledBorder("Job Description"));
        jdArea = new JTextArea();
        right.add(new JScrollPane(jdArea));

        center.add(left);
        center.add(right);
        add(center, BorderLayout.CENTER);

        // Bottom panel for buttons + results
        JPanel bottom = new JPanel(new BorderLayout());
        JPanel btns = new JPanel();

        scanBtn = new JButton(" Scan");
        clearBtn = new JButton(" Clear");

        scanBtn.addActionListener(this);
        clearBtn.addActionListener(this);

        btns.add(scanBtn);
        btns.add(clearBtn);

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        bottom.add(btns, BorderLayout.NORTH);
        bottom.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == clearBtn) {
            resumeArea.setText("");
            jdArea.setText("");
            resultArea.setText("");
        }

        if (e.getSource() == scanBtn) {

            String resume = resumeArea.getText().toLowerCase();
            String[] jdWords = jdArea.getText().toLowerCase().split(" ");

            int total = jdWords.length, match = 0;

            for (int i = 0; i < jdWords.length; i++)
                if (jdWords[i].length() > 1 && resume.contains(jdWords[i]))
                    match++;

            int score = (match * 100) / total;

            Color bg = Color.red;
            if (score > 70)
                bg = Color.green;
            else if (score >= 50)
                bg = Color.orange;
            resultArea.setBackground(bg);

            resultArea.setText("Score: " + score + "%\n");
            resultArea.append("Matched: " + match + "/" + total + "\n\n");

            resultArea.append("Matching: ");
            for (int i = 0; i < jdWords.length; i++)
                if (resume.contains(jdWords[i]))
                    resultArea.append(jdWords[i] + " ");

            resultArea.append("\nMissing: ");
            for (int i = 0; i < jdWords.length; i++)
                if (!resume.contains(jdWords[i]))
                    resultArea.append(jdWords[i] + " ");
        }
    }

    public static void main(String[] args) {
        new smapleatsscore();
    }
}
