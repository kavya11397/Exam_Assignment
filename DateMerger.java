
import java.util.*;
import java.time.*;



public class DateMerger {

  public static void main(String[] args) throws Exception {
    List<DateRange> dateRang = new ArrayList<>();
    
    
    dateRang.add(new DateRange(LocalDate.of(2014, 1, 1), LocalDate.of(2014, 1, 30)));
    dateRang.add(new DateRange(LocalDate.of(2014, 1, 15), LocalDate.of(2014, 2, 15)));
    dateRang.add(new DateRange(LocalDate.of(2014, 3, 10), LocalDate.of(2014, 4, 15)));
    dateRang.add(new DateRange(LocalDate.of(2014, 4, 10), LocalDate.of(2014, 5, 15)));

    System.out.println("Date range for the given scenario:  \n");
    dateRang.stream().forEach(dateRange -> System.out.println(dateRange.getStartDate() + " - " + dateRange.getEndDate()));

    List<DateRange> mergedDateRange = mergeDates(dateRang);

    System.out.println("\nOutput of the given Date Range:  \n");
    mergedDateRange.stream().forEach(dateRange -> System.out.println(dateRange.getStartDate() + " - " + dateRange.getEndDate()));
  }


  public static  List<DateRange> mergeDates(List<DateRange> dateRanges) {
    Set<DateRange> mergedDateData = new HashSet<>();
    Collections.sort(dateRanges, DateRange.START_DATE_COMPARATOR);

    mergedDateData.add(dateRanges.get(0));
    for (int start = 1; start < dateRanges.size(); start++) {
      DateRange current = dateRanges.get(start);
      List<DateRange> joinData = new ArrayList<>();
      Boolean isMergedData = false;
      for (DateRange mergedRange : mergedDateData) {
        DateRange merged = checkOverlap(mergedRange, current);
        if (merged == null) {
        	joinData.add(current);
        }
        else {
          mergedRange.setEndDate(merged.getEndDate());
          mergedRange.setStartDate(merged.getStartDate());
          isMergedData = true;
          break;
        }
      }
      if (!isMergedData) {
    	  mergedDateData.addAll(joinData);
      }
      joinData.clear();
    }
    List<DateRange> mergedDateRangeList = new ArrayList<>(mergedDateData);
    Collections.sort(mergedDateRangeList, DateRange.START_DATE_COMPARATOR);
    return mergedDateRangeList;
  }


  public static DateRange checkOverlap(DateRange mergedRange, DateRange current) {
    if (mergedRange.getStartDate().isAfter(current.getEndDate()) || mergedRange.getEndDate().isBefore(current.getStartDate())) {
      return null;
    }
    else {
      return new DateRange(mergedRange.getStartDate().isBefore(current.getStartDate()) ? mergedRange.getStartDate() : current.getStartDate(),
        mergedRange.getEndDate().isAfter(current.getEndDate()) ? mergedRange.getEndDate() : current.getEndDate());
    }
  }
}






class DateRange {

	  private LocalDate start_Date;

	  private LocalDate end_Date;

	  public DateRange(LocalDate start_Date, LocalDate end_Date) {
	    this.start_Date = start_Date;
	    this.end_Date = end_Date;
	  }

	  public LocalDate getStartDate() {
	    return start_Date;
	  }

	  public void setStartDate(LocalDate start_Date) {
	    this.start_Date = start_Date;
	  }

	  public LocalDate getEndDate() {
	    return end_Date;
	  }

	  public void setEndDate(LocalDate end_Date) {
	    this.end_Date = end_Date;
	  }

	  public static final Comparator<DateRange> START_DATE_COMPARATOR = (DateRange data_Range1, DateRange data_Range2) -> {
	    if (data_Range1.getStartDate() != null && data_Range2.getStartDate() != null) {
	      if (data_Range1.getStartDate().isBefore(data_Range2.getStartDate())) {
	        return -1;
	      }
	      else {
	        return data_Range1.getStartDate().isAfter(data_Range2.getStartDate()) ? 1 : 0;
	      }
	    }
	    else if (data_Range1.getStartDate() != null && data_Range2.getStartDate() == null) {
	      return -1;
	    }
	    else if (data_Range1.getStartDate() == null && data_Range2.getStartDate() != null) {
	      return 1;
	    }
	    else {
	      return 0;
	    }
	  };

	  @Override
	  public int hashCode() {
	    final int isPrime = 31;
	    int output = 1;
	    output = isPrime * output + ((end_Date == null) ? 0 : end_Date.hashCode());
	    output = isPrime * output + ((start_Date == null) ? 0 : start_Date.hashCode());
	    return output;
	  }

	  @Override
	  public boolean equals(Object object) {
	    if (this == object)
	      return true;
	    if (object == null)
	      return false;
	    if (getClass() != object.getClass())
	      return false;
	    DateRange data_Range_Obj = (DateRange) object;
	    if (end_Date == null) {
	      if (data_Range_Obj.end_Date != null)
	        return false;
	    }
	    else if (!end_Date.equals(data_Range_Obj.end_Date))
	      return false;
	    if (start_Date == null) {
	      if (data_Range_Obj.start_Date != null)
	        return false;
	    }
	    else if (!start_Date.equals(data_Range_Obj.start_Date))
	      return false;
	    return true;
	  }
	}