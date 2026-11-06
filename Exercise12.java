package chapter6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

public class Exercise12 {

	private static final Concert CONCERT = new Concert("Concert 1", "Artist X", 100);
	private static final Movie MOVIE1 = new Movie("Movie 1", 2017, 120);
	private static final Movie MOVIE2 = new Movie("Movie 2", 2018, 130);
	private static final CompositeShow COMPOSITE = new CompositeShow(CONCERT, new CompositeShow(MOVIE1, MOVIE2));
	private static final IntroducedShow DECORATOR = new IntroducedShow(
			"Speaker 1", 10, new CompositeShow(CONCERT, new CompositeShow(MOVIE1,
					new IntroducedShow("Speaker 2", 5, MOVIE2))));

	@Test
	void testCopy_NotSame() {
		IntroducedShow copy = DECORATOR.copy();
		assertNotSame(DECORATOR, copy);
	}

	@Test
	void testCopy_Equal() {
		IntroducedShow copy = DECORATOR.copy();
		assertEquals(DECORATOR, copy);
	}

	@Test
	void testCopy_CompositeChildrenNotShared() {
		CompositeShow copy = COMPOSITE.copy();
		Iterator<Show> original = COMPOSITE.iterator();
		Iterator<Show> copied = copy.iterator();
		while (original.hasNext()) {
			assertNotSame(original.next(), copied.next());
		}
	}
}