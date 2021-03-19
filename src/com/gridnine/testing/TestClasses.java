package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Factory class to get sample list of flights.
 * Заводской класс, чтобы получить примерный список рейсов
 */
class FlightBuilder {
    static List<Flight> createFlights() {
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
            List<Flight> list = Arrays.asList(
            //A normal flight with two hour duration
                //Обычный полет продолжительностью два часа
            createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
            //A normal multi segment flight
                //Обычный многосегментный полет
            createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
            //A flight departing in the past
                //Рейс вылетает в прошлое
            createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow),
            //A flight that departs before it arrives
                //Рейс, который отправляется до прибытия
            createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6)),
            //A flight with more than two hours ground time
                //Полет с наземным временем более двух часов
            createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),
            //Another flight with more than two hours ground time
                //Другой рейс с наземным временем более двух часов
            createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)));
//        for (Flight flight:list ) {
//            flight.getSegments().get(0).arrivalDate.getDayOfMonth()

//            System.out.println(flight);
                int sum =0;
                List<Flight> flightListFinish = new ArrayList<>();
            for (int i = 0; i <list.size() ; i++) {
                sum=0;
//                System.out.println(list);
                for (int j = 0; j <list.get(i).getSegments().size() ; j++) {

                    if(list.get(i).getSegments().get(sum).getArrivalDate().getHour()>LocalDateTime.now().getHour()&&list.get(i).getSegments().get(sum).getArrivalDate().getDayOfMonth()>LocalDateTime.now().getDayOfMonth()&&list.get(i).getSegments().get(sum).getArrivalDate().getHour()<=LocalDateTime.now().getHour()+2){
                        System.out.println(list.get(i).getSegments().get(sum).getArrivalDate());
//                        flightListFinish.add(list.get(i).getSegments().get(sum).getArrivalDate());
//                        System.out.println(list.get(i).getSegments().get(sum).getArrivalDate().getDayOfMonth());
                    }sum++;
                }


            }
//                if(flight.getSegments().get(1).getArrivalDate().getDayOfMonth()>(LocalDateTime.now().getDayOfMonth()) && flight.getSegments().get(1).getArrivalDate().getHour()>(LocalDateTime.now().getHour())){
//                    System.out.println(flight.getSegments().get(1).getArrivalDate().getDayOfMonth());
//                }
//            }


//        }
        return null ;
    }

    private static Flight createFlight(final LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                "you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }
}

/**
 * Bean that represents a flight.
 * Бин, представляющий полет.
 */
class Flight {
    private final List<Segment> segments;

    Flight(final List<Segment> segs) {
        segments = segs;
    }

    List<Segment> getSegments() {
        return segments;
    }


    @Override
    public String toString() {
        return segments.stream().map(Object::toString)
            .collect(Collectors.joining(" "));
    }
}

/**
 * Bean that represents a flight segment.
 * Бин, представляющий полетный сегмент.
 */
class Segment {
    private final LocalDateTime departureDate;

    private final LocalDateTime arrivalDate;

    Segment(final LocalDateTime dep, final LocalDateTime arr) {
        departureDate = Objects.requireNonNull(dep);
        arrivalDate = Objects.requireNonNull(arr);
    }

    LocalDateTime getDepartureDate() {
        return departureDate;
    }

    LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return '[' + departureDate.format(fmt) + '|' + arrivalDate.format(fmt)
            + ']';
    }
}
