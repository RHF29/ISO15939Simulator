package data;

import model.Dimension;
import model.Metric;
import model.Scenario;

import java.util.ArrayList;
import java.util.HashMap;

public class ScenarioRepository {
    private HashMap<String, ArrayList<Scenario>> scenariosByMode;

    public ScenarioRepository() {
        scenariosByMode = new HashMap<>();
        loadScenarios();
    }

    private void loadScenarios() {
        ArrayList<Scenario> educationScenarios = new ArrayList<>();
        educationScenarios.add(createEducationScenarioC());
        educationScenarios.add(createEducationScenarioD());

        ArrayList<Scenario> healthScenarios = new ArrayList<>();
        healthScenarios.add(createHealthScenarioA());
        healthScenarios.add(createHealthScenarioB());

        scenariosByMode.put("Education", educationScenarios);
        scenariosByMode.put("Health", healthScenarios);
    }

    public ArrayList<Scenario> getScenariosByMode(String mode) {
        return scenariosByMode.get(mode);
    }

    private Scenario createEducationScenarioC() {
        Scenario scenario = new Scenario("Education", "Scenario C - Team Alpha");

        Dimension usability = new Dimension("Usability", 25);
        usability.addMetric(new Metric("SUS score", 50, "Higher", 0, 100, "points", 89));
        usability.addMetric(new Metric("Onboarding time", 50, "Lower", 0, 60, "min", 5));

        Dimension performance = new Dimension("Performance Efficiency", 20);
        performance.addMetric(new Metric("Video start time", 50, "Lower", 0, 15, "sec", 3));
        performance.addMetric(new Metric("Concurrent exams", 50, "Higher", 0, 600, "users", 520));

        Dimension accessibility = new Dimension("Accessibility", 20);
        accessibility.addMetric(new Metric("WCAG compliance", 50, "Higher", 0, 100, "%", 82));
        accessibility.addMetric(new Metric("Screen reader score", 50, "Higher", 0, 100, "%", 76));

        Dimension reliability = new Dimension("Reliability", 20);
        reliability.addMetric(new Metric("Uptime", 50, "Higher", 95, 100, "%", 98.8));
        reliability.addMetric(new Metric("MTTR", 50, "Lower", 0, 120, "min", 28));

        Dimension functionality = new Dimension("Functional Suitability", 15);
        functionality.addMetric(new Metric("Feature completion", 50, "Higher", 0, 100, "%", 91));
        functionality.addMetric(new Metric("Assignment submit rate", 50, "Higher", 0, 100, "%", 87));

        scenario.addDimension(usability);
        scenario.addDimension(performance);
        scenario.addDimension(accessibility);
        scenario.addDimension(reliability);
        scenario.addDimension(functionality);

        return scenario;
    }

    private Scenario createEducationScenarioD() {
        Scenario scenario = new Scenario("Education", "Scenario D - Team Beta");

        Dimension usability = new Dimension("Usability", 30);
        usability.addMetric(new Metric("Student satisfaction", 50, "Higher", 0, 100, "%", 72));
        usability.addMetric(new Metric("Task completion time", 50, "Lower", 0, 90, "min", 38));

        Dimension performance = new Dimension("Performance Efficiency", 25);
        performance.addMetric(new Metric("Page load time", 50, "Lower", 0, 10, "sec", 4));
        performance.addMetric(new Metric("Quiz response speed", 50, "Lower", 0, 8, "sec", 2));

        Dimension reliability = new Dimension("Reliability", 25);
        reliability.addMetric(new Metric("System uptime", 50, "Higher", 95, 100, "%", 97.5));
        reliability.addMetric(new Metric("Error rate", 50, "Lower", 0, 10, "%", 3));

        Dimension support = new Dimension("Support Quality", 20);
        support.addMetric(new Metric("Ticket resolution", 50, "Higher", 0, 100, "%", 80));
        support.addMetric(new Metric("Average response time", 50, "Lower", 0, 48, "hours", 10));

        scenario.addDimension(usability);
        scenario.addDimension(performance);
        scenario.addDimension(reliability);
        scenario.addDimension(support);

        return scenario;
    }

    private Scenario createHealthScenarioA() {
        Scenario scenario = new Scenario("Health", "Scenario A - Hospital System");

        Dimension usability = new Dimension("Usability", 25);
        usability.addMetric(new Metric("Doctor satisfaction", 50, "Higher", 0, 100, "%", 84));
        usability.addMetric(new Metric("Patient lookup time", 50, "Lower", 0, 120, "sec", 25));

        Dimension security = new Dimension("Security", 30);
        security.addMetric(new Metric("Access control score", 50, "Higher", 0, 100, "%", 92));
        security.addMetric(new Metric("Unauthorized attempts", 50, "Lower", 0, 50, "count", 4));

        Dimension reliability = new Dimension("Reliability", 25);
        reliability.addMetric(new Metric("System uptime", 50, "Higher", 95, 100, "%", 99.1));
        reliability.addMetric(new Metric("Recovery time", 50, "Lower", 0, 120, "min", 18));

        Dimension performance = new Dimension("Performance", 20);
        performance.addMetric(new Metric("Report generation time", 50, "Lower", 0, 60, "sec", 12));
        performance.addMetric(new Metric("Concurrent users", 50, "Higher", 0, 1000, "users", 740));

        scenario.addDimension(usability);
        scenario.addDimension(security);
        scenario.addDimension(reliability);
        scenario.addDimension(performance);

        return scenario;
    }

    private Scenario createHealthScenarioB() {
        Scenario scenario = new Scenario("Health", "Scenario B - Clinic Portal");

        Dimension usability = new Dimension("Usability", 25);
        usability.addMetric(new Metric("Patient satisfaction", 50, "Higher", 0, 100, "%", 78));
        usability.addMetric(new Metric("Appointment booking time", 50, "Lower", 0, 30, "min", 9));

        Dimension accessibility = new Dimension("Accessibility", 20);
        accessibility.addMetric(new Metric("Mobile accessibility", 50, "Higher", 0, 100, "%", 86));
        accessibility.addMetric(new Metric("Form error clarity", 50, "Higher", 0, 100, "%", 74));

        Dimension security = new Dimension("Security", 30);
        security.addMetric(new Metric("Encryption coverage", 50, "Higher", 0, 100, "%", 95));
        security.addMetric(new Metric("Failed login rate", 50, "Lower", 0, 20, "%", 5));

        Dimension reliability = new Dimension("Reliability", 25);
        reliability.addMetric(new Metric("Portal uptime", 50, "Higher", 95, 100, "%", 98.2));
        reliability.addMetric(new Metric("Incident resolution time", 50, "Lower", 0, 180, "min", 42));

        scenario.addDimension(usability);
        scenario.addDimension(accessibility);
        scenario.addDimension(security);
        scenario.addDimension(reliability);

        return scenario;
    }
}