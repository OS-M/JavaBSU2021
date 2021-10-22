package by.zhabdex.monitoring;

import by.derovi.service_monitoring.visualizer.TerminalRenderer;
import by.zhabdex.common.Service;
import by.zhabdex.common.Tools;
import by.zhabdex.monitoring.collections.*;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Main {
    public static List<Service> fetchServices() throws IOException {
        return Tools.JSON.readValue(
                new URL("http://zhabdex.ovi.by/status"),
                new TypeReference<List<Service>>() {
                });
    }

    public static void main(String[] args) throws IOException {
//        MappedCollection<Integer, String> collection
//                = new MappedCollection<>((Integer a) -> "Number is " + a.toString());

//        collection.renew(List.of(4, 5, 6, 7));
//        System.out.println(collection.currentState());
//        collection.currentState().forEach(System.out::println);

//        FilteredCollection<Integer> collection
//                = new FilteredCollection<>(value -> value > 5);
//
//        collection.renew(List.of(4, 5, 6, 7));
//        collection.currentState().forEach(System.out::println);

//        LimitedCollection<Integer> collection
//                = new LimitedCollection<>(2);
//
//        collection.renew(List.of(4, 7, 6, 7));
//        collection.currentState().forEach(System.out::println);

//        ReducedCollection<Integer> collection
//                = new ReducedCollection<>(Integer::sum);
//
//        collection.renew(List.of(4, 5, 6, 7));
//        System.out.println(collection.currentState().get());

//        ReversedCollection<Integer> collection = new ReversedCollection<>();
//
//        collection.renew(List.of(4, 5, 6, 7));
//        collection.currentState().forEach(System.out::println);

//        SortedCollection<Service> collection
//                = new SortedCollection<>(Service::getNodesCount);
//
//        collection.renew(fetchServices());
//        collection.currentState().forEach(service -> System.out.println(service.getNodesCount()));

//        SortedCollection<Service> collection
//                = new SortedCollection<>((first, second) -> second.getNodesCount() - first.getNodesCount());
//
//        collection.renew(fetchServices());
//        collection.currentState().forEach(service -> System.out.println(service.getNodesCount()));

//        var collection =
//                new FilteredCollection<Integer>(x -> x % 2 == 0)
//                        .compose(new SortedCollection<>((a, b) -> b - a))
//                        .compose(new MappedCollection<>(a -> "This is " + a.toString()));
////        collection.renew(List.of(4, 5, 6, 7));
//        System.out.println(collection.currentState());

        var collection =
                new SortedCollection<>(Service::getRequestsForUptime).compose(
                        new TableViewCollection<>("Test", List.of(
                                TableViewCollection.ColumnProvider.of(
                                        "Name", Service::getName),
                                TableViewCollection.ColumnProvider.of(
                                        "Data center", Service::getDataCenter),
                                TableViewCollection.ColumnProvider.of(
                                        "Summary ping", Service::getSummaryPing),
                                TableViewCollection.ColumnProvider.of(
                                        "Requests for uptime", Service::getRequestsForUptime),
                                TableViewCollection.ColumnProvider.of(
                                        "Ping", Service::getAveragePing),
                                TableViewCollection.ColumnProvider.of(
                                        "Available nodes", Service::getNodesCount),
                                TableViewCollection.ColumnProvider.of(
                                        "Requests/sec", Service::getRequestsPerSecond),
                                TableViewCollection.ColumnProvider.of(
                                        "Started time", Service::getStartedTime),
                                TableViewCollection.ColumnProvider.of(
                                        "Current time", Service::getCurrentTime)
                        ))
                );

        TerminalRenderer renderer = TerminalRenderer.init(1);
        LocalDateTime lastUpdate = LocalDateTime.MIN;
        while (true) {
            if (ChronoUnit.SECONDS.between(lastUpdate, LocalDateTime.now()) >= 1) {
                lastUpdate = LocalDateTime.now();
                collection.renew(fetchServices());
                renderer.render(List.of(collection.currentState()));
            }
        }
    }
}
