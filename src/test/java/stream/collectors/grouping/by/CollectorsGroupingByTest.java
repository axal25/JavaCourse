package stream.collectors.grouping.by;

import com.google.common.truth.Correspondence;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static com.google.common.truth.Truth.assertThat;
import static java.util.stream.Collectors.*;

public class CollectorsGroupingByTest {
    private static final BiFunction<LongSummaryStatistics, LongSummaryStatistics, Boolean>
            LONG_SUMMARY_STATISTICS_EQUALS = (a, b) ->
            Objects.equals(a.getCount(), b.getCount())
                    && Objects.equals(a.getMin(), b.getMin())
                    && Objects.equals(a.getMax(), b.getMax())
                    && Objects.equals(a.getSum(), b.getSum())
                    && Objects.equals(a.getAverage(), b.getAverage());
    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    private static class Person {
        private final Long id;
        private final String firstName;
        private final List<String> names;
        private final String lastName;

        @Builder
        @Getter
        @ToString
        private static class Record {
            private final List<Person> persons;
            private final long count;
            private final LongSummaryStatistics summaryStatisticsForIds;

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Record)) return false;
                Record record = (Record) o;
                return count == record.count
                        && Objects.equals(persons, record.persons)
                        && LONG_SUMMARY_STATISTICS_EQUALS.apply(summaryStatisticsForIds, record.summaryStatisticsForIds);
            }

            @Override
            public int hashCode() {
                return Objects.hash(persons, count, summaryStatisticsForIds);
            }
        }
    }

    @Test
    void groupingBy1Field_defaultSubCollectionIsList_thereAreRepeatedRecords() {
        Person person1 = Person.builder()
                .firstName("firstName1")
                .lastName("lastName1")
                .build();
        Person person2SameLastNameAsPerson1 = Person.builder()
                .firstName("firstName2")
                .lastName("lastName1")
                .build();
        Person person3 = Person.builder()
                .firstName("firstName3")
                .lastName("lastName3")
                .build();
        Person person4 = Person.builder()
                .firstName("firstName1")
                .lastName("lastName4")
                .build();

        List<Person> persons = List.of(
                person1,
                // repetition 1
                person1,
                person2SameLastNameAsPerson1,
                // repetition 2
                person2SameLastNameAsPerson1,
                person3,
                // repetition 3
                person3,
                person4,
                // repetition 4,
                person4);

        Map<String, List<Person>> lastNameToListOfPersonMap =
                persons.stream().collect(groupingBy(Person::getLastName));

        assertThat(lastNameToListOfPersonMap).isEqualTo(Map.of(
                person1.getLastName(), List.of(
                        person1,
                        // repetition 1
                        person1,
                        person2SameLastNameAsPerson1,
                        // repetition 2
                        person2SameLastNameAsPerson1),
                person3.getLastName(), List.of(
                        person3,
                        // repetition 3
                        person3
                ),
                person4.getLastName(), List.of(
                        person4,
                        // repetition 4
                        person4)));
        assertThat(lastNameToListOfPersonMap)
                .isEqualTo(persons.stream().collect(groupingBy(Person::getLastName,
                        // default sub-collection is list
                        toList())));
    }

    @Test
    void groupingBy1Field_toSet_noRepeatedRecordsInSubCollection() {
        Person person1 = Person.builder()
                .firstName("firstName1")
                .lastName("lastName1")
                .build();
        Person person2SameLastNameAsPerson1 = Person.builder()
                .firstName("firstName2")
                .lastName("lastName1")
                .build();
        Person person3 = Person.builder()
                .firstName("firstName3")
                .lastName("lastName3")
                .build();
        Person person4 = Person.builder()
                .firstName("firstName1")
                .lastName("lastName4")
                .build();
        List<Person> persons = List.of(
                person1,
                // repetition 1
                person1,
                person2SameLastNameAsPerson1,
                // repetition 2
                person2SameLastNameAsPerson1,
                person3,
                // repetition 3
                person3,
                person4,
                // repetition 4
                person4);

        Map<String, Set<Person>> lastNameToSetOfPersonMap =
                persons.stream().collect(groupingBy(Person::getLastName, toSet()));

        // no repetitions
        assertThat(lastNameToSetOfPersonMap).isEqualTo(Map.of(
                person1.getLastName(), Set.of(person1, person2SameLastNameAsPerson1),
                person3.getLastName(), Set.of(person3),
                person4.getLastName(), Set.of(person4)));
    }

    @Test
    void groupingByMultipleFields_usingManualAbstractMapSimpleEntry() {
        Person person1 = Person.builder()
                .firstName("firstName1")
                .names(List.of("SecondName1"))
                .lastName("lastName1")
                .build();
        Person person2SameFirstAndLastNameAsPerson1 = Person.builder()
                .firstName("firstName1")
                .names(List.of("SecondName2"))
                .lastName("lastName1")
                .build();
        Person person3 = Person.builder()
                .firstName("firstName3")
                .names(List.of("SecondName3"))
                .lastName("lastName3")
                .build();
        Person person4 = Person.builder()
                .firstName("firstName1")
                .names(List.of("SecondName4"))
                .lastName("lastName4")
                .build();

        List<Person> persons = List.of(person1, person2SameFirstAndLastNameAsPerson1, person3, person4);

        Map<Entry<String, String>, List<Person>> firstAndLastNameToListOfPersonMap = persons.stream()
                .collect(groupingBy(person -> new SimpleEntry<>(person.getFirstName(), person.getLastName())));

        assertThat(firstAndLastNameToListOfPersonMap).isEqualTo(Map.of(
                new SimpleEntry<>(person1.getFirstName(), person1.getLastName()),
                List.of(person1, person2SameFirstAndLastNameAsPerson1),
                new SimpleEntry<>(person3.getFirstName(), person3.getLastName()),
                List.of(person3),
                new SimpleEntry<>(person4.getFirstName(), person4.getLastName()),
                List.of(person4)));
    }

    @Test
    void groupingByMultipleFields_usingChainedGroupingBy() {
        Person person1 = Person.builder()
                .firstName("firstName1")
                .names(List.of("SecondName1"))
                .lastName("lastName1")
                .build();
        Person person2SameFirstAndLastNameAsPerson1 = Person.builder()
                .firstName("firstName1")
                .names(List.of("SecondName2"))
                .lastName("lastName1")
                .build();
        Person person3 = Person.builder()
                .firstName("firstName3")
                .names(List.of("SecondName3"))
                .lastName("lastName3")
                .build();
        Person person4 = Person.builder()
                .firstName("firstName1")
                .names(List.of("SecondName4"))
                .lastName("lastName4")
                .build();

        List<Person> persons = List.of(person1, person2SameFirstAndLastNameAsPerson1, person3, person4);

        Map<String, Map<String, List<Person>>> firstNameToLastNameToListOfPersonMapMap = persons.stream().collect(
                groupingBy(Person::getFirstName, groupingBy(Person::getLastName)));

        assertThat(firstNameToLastNameToListOfPersonMapMap).isEqualTo(Map.of(
                person1.getFirstName(), Map.of(
                        person1.getLastName(), List.of(person1, person2SameFirstAndLastNameAsPerson1),
                        person4.getLastName(), List.of(person4)),
                person3.getFirstName(), Map.of(
                        person3.getLastName(), List.of(person3))));

        Map<Entry<String, String>, List<Person>> firstAndLastNameToListOfPersonMap =
                firstNameToLastNameToListOfPersonMapMap.entrySet().stream()
                        .flatMap(firstNameToMap -> firstNameToMap.getValue().entrySet().stream()
                                .map(lastNameToListOfPersonMap -> new SimpleEntry<>(
                                        new SimpleEntry<>(
                                                firstNameToMap.getKey(),
                                                lastNameToListOfPersonMap.getKey()),
                                        lastNameToListOfPersonMap.getValue())))
                        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

        assertThat(firstAndLastNameToListOfPersonMap)
                .isEqualTo(persons.stream().collect(groupingBy(person ->
                        new SimpleEntry<>(person.getFirstName(), person.getLastName()))));

        assertThat(firstAndLastNameToListOfPersonMap)
                .isEqualTo(Map.of(
                        new SimpleEntry<>(person1.getFirstName(), person1.getLastName()),
                        List.of(person1, person2SameFirstAndLastNameAsPerson1),
                        new SimpleEntry<>(person3.getFirstName(), person3.getLastName()),
                        List.of(person3),
                        new SimpleEntry<>(person4.getFirstName(), person4.getLastName()),
                        List.of(person4)
                ));
    }

    @Test
    void groupingBy_aggregateCounting() {
        Person person1 = Person.builder()
                .firstName("firstName1")
                .lastName("lastName1")
                .build();
        Person person2SameLastNameAsPerson1 = Person.builder()
                .firstName("firstName2")
                .lastName("lastName1")
                .build();
        Person person3 = Person.builder()
                .firstName("firstName3")
                .lastName("lastName3")
                .build();
        Person person4 = Person.builder()
                .firstName("firstName1")
                .lastName("lastName4")
                .build();
        List<Person> persons = List.of(person1, person2SameLastNameAsPerson1, person3, person4);

        Map<String, Long> lastNameToPersonCountMap =
                persons.stream().collect(groupingBy(Person::getLastName,
                        Collectors.counting()));

        assertThat(lastNameToPersonCountMap).isEqualTo(Map.of(
                person1.getLastName(), 2L,
                person3.getLastName(), 1L,
                person4.getLastName(), 1L));
        Map<String, List<Person>> lastNameToListOfPersonMap =
                persons.stream().collect(groupingBy(Person::getLastName));
        assertThat(lastNameToListOfPersonMap).isEqualTo(Map.of(
                person1.getLastName(), List.of(person1, person2SameLastNameAsPerson1),
                person3.getLastName(), List.of(person3),
                person4.getLastName(), List.of(person4)));
        assertThat(lastNameToPersonCountMap).isEqualTo(lastNameToListOfPersonMap.entrySet().stream()
                .collect(toMap(
                        Entry::getKey,
                        lastNameToListOfPerson -> Long.valueOf(
                                lastNameToListOfPerson.getValue().size()))));
    }

    @Test
    void groupingBy_aggregateAveragingLong() {
        Person person1 = Person.builder()
                .id(0L)
                .firstName("firstName1")
                .lastName("lastName1")
                .build();
        Person person2SameLastNameAsPerson1 = Person.builder()
                .id(1L)
                .firstName("firstName2")
                .lastName("lastName1")
                .build();
        Person person3 = Person.builder()
                .id(2L)
                .firstName("firstName3")
                .lastName("lastName3")
                .build();
        Person person4 = Person.builder()
                .id(3L)
                .firstName("firstName1")
                .lastName("lastName4")
                .build();
        List<Person> persons = List.of(person1, person2SameLastNameAsPerson1, person3, person4);

        Map<String, Double> lastNameToPersonIdAverageMap =
                persons.stream().collect(groupingBy(Person::getLastName,
                        Collectors.averagingLong(Person::getId)));
        assertThat(lastNameToPersonIdAverageMap)
                .isEqualTo(Map.of(
                        person1.getLastName(),
                        person1.getId() + person2SameLastNameAsPerson1.getId()
                                / 2D,
                        person3.getLastName(),
                        Double.valueOf(
                                person3.getId()),
                        person4.getLastName(),
                        Double.valueOf(
                                person4.getId())));
    }

    @Test
    void groupingBy_aggregateSummingInt() {
        Person person1 = Person.builder()
                .id(0L)
                .firstName("firstName1")
                .lastName("lastName1")
                .build();
        Person person2SameLastNameAsPerson1 = Person.builder()
                .id(1L)
                .firstName("firstName2")
                .lastName("lastName1")
                .build();
        Person person3 = Person.builder()
                .id(2L)
                .firstName("firstName3")
                .lastName("lastName3")
                .build();
        Person person4 = Person.builder()
                .id(3L)
                .firstName("firstName1")
                .lastName("lastName4")
                .build();
        List<Person> persons = List.of(person1, person2SameLastNameAsPerson1, person3, person4);

        Map<String, Long> lastNameToPersonIdSumMap = persons.stream().collect(groupingBy(Person::getLastName,
                Collectors.summingLong(Person::getId)));

        assertThat(lastNameToPersonIdSumMap)
                .isEqualTo(Map.of(
                        person1.getLastName(), person1.getId() + person2SameLastNameAsPerson1.getId(),
                        person3.getLastName(), person3.getId(),
                        person4.getLastName(), person4.getId()));
    }

    @Test
    void groupingBy_aggregateMaxBy_comparingInt_personGetId() {
        Person person1 = Person.builder()
                .id(0L)
                .firstName("firstName1")
                .lastName("lastName1")
                .build();
        Person person2SameLastNameAsPerson1 = Person.builder()
                .id(1L)
                .firstName("firstName2")
                .lastName("lastName1")
                .build();
        Person person3 = Person.builder()
                .id(2L)
                .firstName("firstName3")
                .lastName("lastName3")
                .build();
        Person person4 = Person.builder()
                .id(3L)
                .firstName("firstName1")
                .lastName("lastName4")
                .build();
        List<Person> persons = List.of(person1, person2SameLastNameAsPerson1, person3, person4);

        Map<String, Optional<Person>> lastNameToOptPersonWithMaxId = persons.stream().collect(
                groupingBy(Person::getLastName,
                        Collectors.maxBy(
                                Comparator.comparingLong(
                                        Person::getId))));

        assertThat(lastNameToOptPersonWithMaxId).isEqualTo(Map.of(
                person1.getLastName(), Optional.of(person2SameLastNameAsPerson1),
                person3.getLastName(), Optional.of(person3),
                person4.getLastName(), Optional.of(person4)));
        assertThat(lastNameToOptPersonWithMaxId.get("nonExistentLastName")).isNull();
    }

    @Test
    void groupingBy_aggregateMinBy_comparingInt_personGetId() {
        Person person1 = Person.builder()
                .id(0L)
                .firstName("firstName1")
                .lastName("lastName1")
                .build();
        Person person2SameLastNameAsPerson1 = Person.builder()
                .id(1L)
                .firstName("firstName2")
                .lastName("lastName1")
                .build();
        Person person3 = Person.builder()
                .id(2L)
                .firstName("firstName3")
                .lastName("lastName3")
                .build();
        Person person4 = Person.builder()
                .id(3L)
                .firstName("firstName1")
                .lastName("lastName4")
                .build();
        List<Person> persons = List.of(person1, person2SameLastNameAsPerson1, person3, person4);

        Map<String, Optional<Person>> lastNameToOptPersonWithMinId = persons.stream().collect(
                groupingBy(Person::getLastName,
                        Collectors.minBy(
                                Comparator.comparingLong(
                                        Person::getId))));

        assertThat(lastNameToOptPersonWithMinId).isEqualTo(Map.of(
                person1.getLastName(), Optional.of(person1),
                person3.getLastName(), Optional.of(person3),
                person4.getLastName(), Optional.of(person4)));
        assertThat(lastNameToOptPersonWithMinId.get("nonExistentLastName")).isNull();
    }

    @Test
    void groupingBy_summarizing() {
        Person person1 = Person.builder()
                .id(0L)
                .firstName("firstName1")
                .lastName("lastName1")
                .build();
        Person person2SameLastNameAsPerson1 = Person.builder()
                .id(1L)
                .firstName("firstName2")
                .lastName("lastName1")
                .build();
        Person person3 = Person.builder()
                .id(2L)
                .firstName("firstName3")
                .lastName("lastName3")
                .build();
        Person person4 = Person.builder()
                .id(3L)
                .firstName("firstName1")
                .lastName("lastName4")
                .build();
        List<Person> persons = List.of(person1, person2SameLastNameAsPerson1, person3, person4);

        Map<String, LongSummaryStatistics> lastNameToPersonIdSummaryStatics =
                persons.stream().collect(groupingBy(Person::getLastName,
                        Collectors.summarizingLong(
                                Person::getId)));

        Correspondence<LongSummaryStatistics, LongSummaryStatistics> correspondence =
                Correspondence.from(
                        LONG_SUMMARY_STATISTICS_EQUALS::apply,
                        LongSummaryStatistics.class.getSimpleName() + " are equal.");
        assertThat(lastNameToPersonIdSummaryStatics)
                .comparingValuesUsing(correspondence)
                .containsExactly(
                        person1.getLastName(), new LongSummaryStatistics(
                                List.of(person1, person2SameLastNameAsPerson1).size(),
                                Math.min(person1.getId(), person2SameLastNameAsPerson1.getId()),
                                Math.max(person1.getId(), person2SameLastNameAsPerson1.getId()),
                                person1.getId() + person2SameLastNameAsPerson1.getId()),
                        person3.getLastName(), new LongSummaryStatistics(
                                List.of(person3).size(),
                                person3.getId(),
                                person3.getId(),
                                person3.getId()),
                        person4.getLastName(), new LongSummaryStatistics(
                                List.of(person4).size(),
                                person4.getId(),
                                person4.getId(),
                                person4.getId()));
        assertThat(lastNameToPersonIdSummaryStatics.get(person1.getLastName()).getAverage())
                .isEqualTo(person1.getId() + person2SameLastNameAsPerson1.getId() / 2D);
        assertThat(lastNameToPersonIdSummaryStatics.get(person3.getLastName()).getAverage())
                .isEqualTo(Double.valueOf(person3.getId()));
        assertThat(lastNameToPersonIdSummaryStatics.get(person4.getLastName()).getAverage())
                .isEqualTo(Double.valueOf(person4.getId()));
    }

    @Test
    void groupingBy_multipleAttributes() {
        Person person1 = Person.builder()
                .id(0L)
                .firstName("firstName1")
                .lastName("lastName1")
                .build();
        Person person2SameLastNameAsPerson1 = Person.builder()
                .id(1L)
                .firstName("firstName2")
                .lastName("lastName1")
                .build();
        Person person3 = Person.builder()
                .id(2L)
                .firstName("firstName3")
                .lastName("lastName3")
                .build();
        Person person4 = Person.builder()
                .id(3L)
                .firstName("firstName1")
                .lastName("lastName4")
                .build();
        List<Person> persons = List.of(person1, person2SameLastNameAsPerson1, person3, person4);

        Map<String, Person.Record> lastNameToRecord =
                persons.stream().collect(groupingBy(Person::getLastName,
                        Collectors.collectingAndThen(
                                toList(), listOfPersonsWithGivenLastName ->
                                        Person.Record.builder()
                                                .persons(listOfPersonsWithGivenLastName)
                                                .count(listOfPersonsWithGivenLastName.size())
                                                .summaryStatisticsForIds(
                                                        listOfPersonsWithGivenLastName.stream().collect(
                                                                Collectors.summarizingLong(Person::getId)))
                                                .build())));

        assertThat(lastNameToRecord).containsExactly(
                person1.getLastName(), Person.Record.builder()
                        .count(List.of(person1, person2SameLastNameAsPerson1).size())
                        .persons(List.of(person1, person2SameLastNameAsPerson1))
                        .summaryStatisticsForIds(new LongSummaryStatistics(
                                List.of(person1.getId(), person2SameLastNameAsPerson1.getId()).size(),
                                person1.getId(),
                                person2SameLastNameAsPerson1.getId(),
                                person1.getId() + person2SameLastNameAsPerson1.getId()))
                        .build(),
                person3.getLastName(), Person.Record.builder()
                        .count(List.of(person3).size())
                        .persons(List.of(person3))
                        .summaryStatisticsForIds(new LongSummaryStatistics(
                                List.of(person3.getId()).size(),
                                person3.getId(),
                                person3.getId(),
                                person3.getId()))
                        .build(),
                person4.getLastName(), Person.Record.builder()
                        .count(List.of(person4).size())
                        .persons(List.of(person4))
                        .summaryStatisticsForIds(new LongSummaryStatistics(
                                List.of(person4.getId()).size(),
                                person4.getId(),
                                person4.getId(),
                                person4.getId()))
                        .build());
    }

    @Test
    void todo() {
        // TODO: https://www.baeldung.com/java-groupingby-collector#10-aggregating-multiple-attributes-of-a-grouped-result
    }
}
