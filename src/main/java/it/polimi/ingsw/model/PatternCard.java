package it.polimi.ingsw.model;


public class PatternCard {
    private final WindowPattern front;
    private final WindowPattern back;

    public PatternCard(WindowPattern front, WindowPattern back) {
        this.front = front;
        this.back = back;
    }

    public WindowPattern getFront() {

        return front;
    }

    public WindowPattern getBack() {

        return back;
    }

    @Override
    public String toString() {
        return "PatternCard{" +
                "\nfront=" + front +
                ",\nback=" + back +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatternCard that = (PatternCard) o;
        return front.equals(that.front) &&
                back.equals(that.back);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}