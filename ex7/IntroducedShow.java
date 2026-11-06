package chapter6.ex7;

import java.util.Iterator;
import java.util.Objects;

/**
 * A show introduced by a speaker giving a fixed-time speech (Decorator pattern).
 */
public class IntroducedShow implements Show {

	private final Show aShow;
	private final String aSpeaker;
	private final int aSpeechTime;

	public IntroducedShow(String pName, int pTime, Show pShow) {
		aShow = pShow;
		aSpeaker = pName;
		aSpeechTime = pTime;
	}

	@Override
	public String description() {
		return "[" + aSpeaker + " introduces " + aShow.description() + "]";
	}

	@Override
	public int runningTime() {
		return aSpeechTime + aShow.runningTime();
	}

	@Override
	public Show copy() {
		return new IntroducedShow(aSpeaker, aSpeechTime, aShow.copy());
	}

	/** The decorator delegates to the decorated show. */
	@Override
	public Iterator<Show> iterator() {
		return aShow.iterator();
	}

	@Override
	public int hashCode() {
		return Objects.hash(aShow, aSpeaker, aSpeechTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntroducedShow other = (IntroducedShow) obj;
		return Objects.equals(aShow, other.aShow) && Objects.equals(aSpeaker, other.aSpeaker)
				&& aSpeechTime == other.aSpeechTime;
	}
}