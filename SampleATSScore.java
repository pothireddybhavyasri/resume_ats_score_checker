import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SampleATSScore extends JFrame implements ActionListener {

    JTextArea resumeArea, jdArea, resultArea;
    JButton scanBtn, clearBtn;
    JScrollPane resPane;

    public SampleATSScore() {

        setTitle("Resume ATS Scanner");
        setSize(700, 600);
        setLayout(new BorderLayout());

        JLabel head = new JLabel("Resume ATS Scanner", JLabel.CENTER);
        head.setOpaque(true);
        head.setBackground(new Color(30, 144, 255));
        head.setForeground(Color.white);
        head.setFont(new Font("Arial", Font.BOLD, 24));
        add(head, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(1, 2, 10, 10));
        JPanel left = new JPanel(new BorderLayout());
        JPanel right = new JPanel(new BorderLayout());

        left.setBorder(BorderFactory.createTitledBorder("Paste Resume Text"));
        resumeArea = new JTextArea();
        left.add(new JScrollPane(resumeArea));

        right.setBorder(BorderFactory.createTitledBorder("Job Description"));
        jdArea = new JTextArea();
        right.add(new JScrollPane(jdArea));

        center.add(left);
        center.add(right);
        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        JPanel btns = new JPanel();

        scanBtn = new JButton("Scan");
        clearBtn = new JButton("Clear");

        scanBtn.addActionListener(this);
        clearBtn.addActionListener(this);

        btns.add(scanBtn);
        btns.add(clearBtn);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.BOLD, 26));

        // 45% HEIGHT OF WINDOW
        resPane = new JScrollPane(resultArea);
        resPane.setPreferredSize(new Dimension(700, 260)); // <-- 260px ≈ 45% of 600px window

        bottom.add(btns, BorderLayout.NORTH);
        bottom.add(resPane, BorderLayout.CENTER);

        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == clearBtn) {
            resumeArea.setText("");
            jdArea.setText("");
            resultArea.setText("");
            resultArea.setBackground(Color.white);
        }

        if (e.getSource() == scanBtn) {

            String resume = resumeArea.getText().toLowerCase();
            String[] jdWords = jdArea.getText().toLowerCase().split(" ");

            int total = jdWords.length, match = 0;

            for (int i = 0; i < jdWords.length; i++)
                if (jdWords[i].length() > 1 && resume.contains(jdWords[i]))
                    match++;

            int score = (match * 100) / total;

            // COLOR RULES
            if (score < 50)
                resultArea.setBackground(Color.red);
            else if (score == 75)
                resultArea.setBackground(Color.yellow);
            else
                resultArea.setBackground(Color.green);

            resultArea.setFont(new Font("Arial", Font.BOLD, 26));

            resultArea.setText("");
            resultArea.append("Score: " + score + "%\n");
            resultArea.append("Matched: " + match + " / " + total + "\n");

            // EXPAND RESULT AREA TO 45% AFTER SCAN
            resPane.setPreferredSize(new Dimension(700, 260));
            resPane.revalidate();
        }
    }

    public static void main(String[] args) {
        new SampleATSScore();
    }
}
