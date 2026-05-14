package gui;

import data.ScenarioRepository;
import model.Dimension;
import model.Metric;
import model.Scenario;
import util.ScoreCalculator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JLabel stepLabel;

    private JTextField usernameField;
    private JTextField schoolField;
    private JTextField sessionField;

    private JRadioButton productRadio;
    private JRadioButton processRadio;

    private JComboBox<String> modeComboBox;
    private JComboBox<Scenario> scenarioComboBox;

    private ScenarioRepository repository;
    private Scenario selectedScenario;

    public MainFrame() {
        repository = new ScenarioRepository();

        setTitle("ISO 15939 Measurement Process Simulator");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        stepLabel = new JLabel("Step 1/5: Profile", SwingConstants.CENTER);
        stepLabel.setFont(new Font("Arial", Font.BOLD, 18));
        stepLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(stepLabel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        mainPanel.add(createProfilePanel(), "Profile");
        mainPanel.add(createDefinePanel(), "Define");

        setVisible(true);
    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(80, 220, 80, 220));

        usernameField = new JTextField();
        schoolField = new JTextField();
        sessionField = new JTextField();

        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);

        formPanel.add(new JLabel("School:"));
        formPanel.add(schoolField);

        formPanel.add(new JLabel("Session Name:"));
        formPanel.add(sessionField);

        JButton nextButton = new JButton("Next");

        nextButton.addActionListener(e -> {
            if (usernameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your username to continue.");
                return;
            }

            if (schoolField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your school to continue.");
                return;
            }

            if (sessionField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your session name to continue.");
                return;
            }

            stepLabel.setText("✓ Profile → Step 2/5: Define");
            cardLayout.show(mainPanel, "Define");
        });

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(nextButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createDefinePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(50, 220, 50, 220));

        productRadio = new JRadioButton("Product Quality");
        processRadio = new JRadioButton("Process Quality");

        ButtonGroup qualityGroup = new ButtonGroup();
        qualityGroup.add(productRadio);
        qualityGroup.add(processRadio);

        productRadio.setSelected(true);

        modeComboBox = new JComboBox<>();
        modeComboBox.addItem("Education");
        modeComboBox.addItem("Health");

        scenarioComboBox = new JComboBox<>();
        updateScenarioComboBox();

        modeComboBox.addActionListener(e -> updateScenarioComboBox());

        formPanel.add(new JLabel("Select Quality Type:"));
        formPanel.add(productRadio);
        formPanel.add(processRadio);
        formPanel.add(new JLabel("Select Mode:"));
        formPanel.add(modeComboBox);
        formPanel.add(scenarioComboBox);

        JPanel buttonPanel = new JPanel();

        JButton backButton = new JButton("Back");
        JButton nextButton = new JButton("Next");

        backButton.addActionListener(e -> {
            stepLabel.setText("Step 1/5: Profile");
            cardLayout.show(mainPanel, "Profile");
        });

        nextButton.addActionListener(e -> {
            selectedScenario = (Scenario) scenarioComboBox.getSelectedItem();

            mainPanel.add(createPlanPanel(), "Plan");
            stepLabel.setText("✓ Profile → ✓ Define → Step 3/5: Plan");
            cardLayout.show(mainPanel, "Plan");
        });

        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void updateScenarioComboBox() {
        scenarioComboBox.removeAllItems();

        String selectedMode = (String) modeComboBox.getSelectedItem();
        ArrayList<Scenario> scenarios = repository.getScenariosByMode(selectedMode);

        for (Scenario scenario : scenarios) {
            scenarioComboBox.addItem(scenario);
        }
    }

    private JPanel createPlanPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columns = {"Dimension", "Metric", "Coefficient", "Direction", "Range", "Unit"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Dimension dimension : selectedScenario.getDimensions()) {
            for (Metric metric : dimension.getMetrics()) {
                model.addRow(new Object[]{
                        dimension.getName() + " (" + dimension.getCoefficient() + ")",
                        metric.getName(),
                        metric.getCoefficient(),
                        metric.getDirection(),
                        metric.getMin() + " - " + metric.getMax(),
                        metric.getUnit()
                });
            }
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();

        JButton backButton = new JButton("Back");
        JButton nextButton = new JButton("Next");

        backButton.addActionListener(e -> {
            stepLabel.setText("✓ Profile → Step 2/5: Define");
            cardLayout.show(mainPanel, "Define");
        });

        nextButton.addActionListener(e -> {
            mainPanel.add(createCollectPanel(), "Collect");
            stepLabel.setText("✓ Profile → ✓ Define → ✓ Plan → Step 4/5: Collect");
            cardLayout.show(mainPanel, "Collect");
        });

        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createCollectPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columns = {"Metric", "Direction", "Range", "Value", "Score (1-5)", "Coeff / Unit"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Dimension dimension : selectedScenario.getDimensions()) {
            for (Metric metric : dimension.getMetrics()) {
                double score = ScoreCalculator.calculateScore(metric);
                metric.setScore(score);

                model.addRow(new Object[]{
                        metric.getName(),
                        metric.getDirection(),
                        metric.getMin() + " - " + metric.getMax(),
                        metric.getValue(),
                        metric.getScore(),
                        metric.getCoefficient() + " / " + metric.getUnit()
                });
            }
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();

        JButton backButton = new JButton("Back");
        JButton nextButton = new JButton("Next");

        backButton.addActionListener(e -> {
            stepLabel.setText("✓ Profile → ✓ Define → Step 3/5: Plan");
            cardLayout.show(mainPanel, "Plan");
        });

        nextButton.addActionListener(e -> {
            mainPanel.add(createAnalysePanel(), "Analyse");
            stepLabel.setText("✓ Profile → ✓ Define → ✓ Plan → ✓ Collect → Step 5/5: Analyse");
            cardLayout.show(mainPanel, "Analyse");
        });

        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createAnalysePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        double lowestScore = 6;
        String lowestDimension = "";

        for (Dimension dimension : selectedScenario.getDimensions()) {
            double totalWeightedScore = 0;
            double totalCoefficient = 0;

            for (Metric metric : dimension.getMetrics()) {
                totalWeightedScore += metric.getScore() * metric.getCoefficient();
                totalCoefficient += metric.getCoefficient();
            }

            double dimensionScore = totalWeightedScore / totalCoefficient;

            JProgressBar bar = new JProgressBar(0, 50);
            bar.setValue((int) (dimensionScore * 10));
            bar.setString(dimension.getName() + ": " + String.format("%.2f", dimensionScore));
            bar.setStringPainted(true);

            resultPanel.add(bar);
            resultPanel.add(Box.createVerticalStrut(15));

            if (dimensionScore < lowestScore) {
                lowestScore = dimensionScore;
                lowestDimension = dimension.getName();
            }
        }

        double gap = 5.0 - lowestScore;
        String qualityLevel;

        if (lowestScore >= 4.5) {
            qualityLevel = "Excellent";
        } else if (lowestScore >= 3.5) {
            qualityLevel = "Good";
        } else if (lowestScore >= 2.5) {
            qualityLevel = "Needs Improvement";
        } else {
            qualityLevel = "Poor";
        }

        JTextArea gapArea = new JTextArea();
        gapArea.setEditable(false);
        gapArea.setFont(new Font("Arial", Font.PLAIN, 15));
        gapArea.setText(
                "Gap Analysis\n\n" +
                        "Lowest Dimension: " + lowestDimension + "\n" +
                        "Score: " + String.format("%.2f", lowestScore) + "\n" +
                        "Gap Value: " + String.format("%.2f", gap) + "\n" +
                        "Quality Level: " + qualityLevel + "\n\n" +
                        "This dimension has the lowest score and requires the most improvement."
        );

        resultPanel.add(gapArea);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            stepLabel.setText("✓ Profile → ✓ Define → ✓ Plan → Step 4/5: Collect");
            cardLayout.show(mainPanel, "Collect");
        });

        panel.add(resultPanel, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }
}