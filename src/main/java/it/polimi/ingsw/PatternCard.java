package it.polimi.ingsw;


import java.util.Objects;

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
                "front=" + front +
                ", back=" + back +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatternCard that = (PatternCard) o;
        return Objects.equals(front, that.front) &&
                Objects.equals(back, that.back);
    }

    @Override
    public int hashCode() {

        return Objects.hash(front, back);
    }
}

