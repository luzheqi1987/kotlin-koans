package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate>{
    override fun compareTo(other: MyDate) = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(repeatTimes: RepeatTimes) : MyDate{
    return addTimeIntervals(repeatTimes.timeInterval, repeatTimes.times)
}

operator fun MyDate.plus(timeInterval: TimeInterval) : MyDate{
    return addTimeIntervals(timeInterval, 1)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

data class RepeatTimes(val timeInterval: TimeInterval, val times: Int)

operator fun TimeInterval.times(times: Int) : RepeatTimes = RepeatTimes(this, times)

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate>{
    override fun iterator(): Iterator<MyDate> {
        return DateRangeIterator(this)
    }
}

class DateRangeIterator(val dataRange : DateRange) : Iterator<MyDate>{
    var current: MyDate = dataRange.start
    override fun hasNext(): Boolean {
        return current <= dataRange.endInclusive
    }

    override fun next(): MyDate {
        val result = current;
        if(hasNext()){
            current = current.nextDay()
        }
        return result
    }
}