package com.jpmc.movie.theater;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jpmc.movie.theater.dao.Customer;
import com.jpmc.movie.theater.dao.Movie;
import com.jpmc.movie.theater.dao.Reservation;
import com.jpmc.movie.theater.dao.Showing;
import com.jpmc.movie.theater.enums.LocalDateProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class Theater {

    private final List<Showing> schedule;
    LocalDateProvider provider;

    //choosing builder because this approach can make the code more flexible and easier to read and maintain,
    // especially when there are many parameters involved
    public Theater(LocalDateProvider provider) {
        this.provider = provider;

        Movie spiderMan = Movie.builder().title("Spider-Man: No Way Home").runningTime(Duration.ofMinutes(90))
                .ticketPrice(12.5).specialCode(1).build();

        Movie turningRed = Movie.builder().title("Turning Red").runningTime(Duration.ofMinutes(85)).ticketPrice(11)
                .specialCode(0).build();

        Movie theBatMan = Movie.builder().title("The Batman").runningTime(Duration.ofMinutes(95)).ticketPrice(9)
                .specialCode(0).build();

        schedule = List.of(
                Showing.builder().movie(turningRed).sequenceOfTheDay(1)
                        .showStartTime(LocalDateTime.of(provider.currentDate() , LocalTime.of(9 , 0))).build() ,
                Showing.builder().movie(spiderMan).sequenceOfTheDay(2)
                        .showStartTime(LocalDateTime.of(provider.currentDate() , LocalTime.of(11 , 0))).build() ,
                Showing.builder().movie(theBatMan).sequenceOfTheDay(3)
                        .showStartTime(LocalDateTime.of(provider.currentDate() , LocalTime.of(12 , 50))).build() ,
                Showing.builder().movie(turningRed).sequenceOfTheDay(4)
                        .showStartTime(LocalDateTime.of(provider.currentDate() , LocalTime.of(14 , 30))).build() ,
                Showing.builder().movie(spiderMan).sequenceOfTheDay(5)
                        .showStartTime(LocalDateTime.of(provider.currentDate() , LocalTime.of(16 , 10))).build() ,
                Showing.builder().movie(theBatMan).sequenceOfTheDay(6)
                        .showStartTime(LocalDateTime.of(provider.currentDate() , LocalTime.of(17 , 50))).build() ,
                Showing.builder().movie(turningRed).sequenceOfTheDay(7)
                        .showStartTime(LocalDateTime.of(provider.currentDate() , LocalTime.of(19 , 30))).build() ,
                Showing.builder().movie(spiderMan).sequenceOfTheDay(8)
                        .showStartTime(LocalDateTime.of(provider.currentDate() , LocalTime.of(21 , 10))).build() ,
                Showing.builder().movie(theBatMan).sequenceOfTheDay(9)
                        .showStartTime(LocalDateTime.of(provider.currentDate() , LocalTime.of(23 , 0))).build()
        );
    }

    public static void main(String[] args) {
        Theater theater = new Theater(LocalDateProvider.INSTANCE);
        theater.printScheduleInTextFormat();
        theater.printScheduleInJsonFormat();
    }

    public Reservation reserve(Customer customer , int sequence , int noOfTicketsToReserve) {
        Showing showing;

        if (noOfTicketsToReserve < 0) {
            throw new IllegalArgumentException("Number of tickets must be positive");
        }

        try {
            showing = schedule.get(sequence - 1);
        } catch (RuntimeException ex) {
            throw new IllegalStateException("not able to find any showing for given sequence " + sequence);
        }

        return Reservation.builder().customer(customer).showing(showing).audienceCount(noOfTicketsToReserve).build();
    }

    public void printScheduleInJsonFormat() {

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS , false);

        String finalOutput = schedule.stream().map(showing -> {
            try {
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(Map.of(
                        "sequenceOfTheDay" , showing.getSequenceOfTheDay() ,
                        "showStartTime" , showing.getShowStartTime() ,
                        "title" , showing.getMovie().getTitle() ,
                        "runningTime" , humanReadableFormat(showing.getMovie().getRunningTime()) ,
                        "ticketPrice" , "$" + showing.getMovieFee()
                ));
            } catch (JsonProcessingException e) {
                log.error("Error during JsonProcess in printScheduleInJsonFormat - " , e);
            }
            return null;
        }).collect(Collectors.joining());

        log.info("============== Printing the Movie Schedule in JSON Format ===============");
        log.info("\n" + finalOutput);
    }

    public void printScheduleInTextFormat() {
        log.info("Today's Date - {}" , provider.currentDate());
        log.info("============== Printing the Movie Schedule in Plain Text Format ===============");
        schedule.forEach(s ->
                log.info("{}: {} {} {} ${}" , s.getSequenceOfTheDay() , s.getShowStartTime() , s.getMovie().getTitle() , humanReadableFormat(s.getMovie().getRunningTime()) , s.getMovieFee())
        );
        log.info("===================================================");
    }

    public String humanReadableFormat(Duration duration) {
        long hour = duration.toHoursPart();
        long remainingMin = duration.toMinutesPart();
//        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());
        return String.format("(%s hour%s %s minute%s)" , hour , handlePlural(hour) , remainingMin , handlePlural(remainingMin));
    }

    // (s) postfix should be added to handle plural correctly
    private String handlePlural(long value) {
        return (value == 1) ? StringUtils.EMPTY : "s";
    }
}
