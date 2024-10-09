package Game.project.resources.model.enums;

public enum Suits {
    CUORI(1),
    QUADRI(2),
    FIORI(3),
    PICCHE(4);

    private final int priority;

    Suits(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
