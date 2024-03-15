import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void randomizedTest() {
        StudentArrayDeque<Integer> student = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> expected = new ArrayDequeSolution<>();

        int total = 1000;
        StringBuilder errorMsg = new StringBuilder();
        errorMsg.append("\n");
        for (int i = 0; i < total; i++) {
            int randomNumber = StdRandom.uniform(0, 1000);
            int randomOperation = StdRandom.uniform(0, 6);
            if (expected.isEmpty()) {
                randomOperation -= 2;
            }
            if (randomOperation < 2) {
                student.addFirst(randomNumber);
                expected.addFirst(randomNumber);
                errorMsg.append(String.format("addFirst(%s)\n", randomNumber));
                assertEquals(errorMsg.toString(), expected.size(), student.size());
            } else if (randomOperation < 4) {
                student.addLast(randomNumber);
                expected.addLast(randomNumber);
                errorMsg.append(String.format("addLast(%s)\n", randomNumber));
                assertEquals(errorMsg.toString(), expected.size(), student.size());
            } else if (randomOperation == 4) {
                Integer studentItem = student.removeFirst();
                Integer expectedItem = expected.removeFirst();
                errorMsg.append("removeFirst()\n");
                assertEquals(errorMsg.toString(), expectedItem, studentItem);
            } else if (randomOperation == 5) {
                Integer studentItem = student.removeLast();
                Integer expectedItem = expected.removeLast();
                errorMsg.append("removeLast()\n");
                assertEquals(errorMsg.toString(), expectedItem, studentItem);
            }
        }
    }
}