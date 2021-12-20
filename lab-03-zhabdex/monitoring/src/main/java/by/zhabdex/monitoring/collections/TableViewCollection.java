package by.zhabdex.monitoring.collections;

import by.derovi.service_monitoring.visualizer.Table;
import by.zhabdex.common.Service;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class TableViewCollection<T extends Service> implements FinalProcessedCollection<T, Table> {

    public static class ColumnProvider {

        private ColumnProvider(String title, Function<Service, ?> fieldExtractor) {
            this.title = title;
            this.fieldExtractor = fieldExtractor;
        }

        public static ColumnProvider of(String title, Function<Service, ?> fieldExtractor) {
            return new ColumnProvider(title, fieldExtractor);
        }

        public <K extends Service> String extract(K service) {
            var field = fieldExtractor.apply(service);
            if (field == null) {
                return "null";
            }
            return field.toString();
        }

        public String getTitle() {
            return title;
        }

        public Function<? extends Service, ?> getFieldExtractor() {
            return fieldExtractor;
        }

        private final String title;
        private final Function<Service, ?> fieldExtractor;
    }

    public TableViewCollection(String title, List<ColumnProvider> providers) {
        this.title = title;
        this.providers = providers;
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        table = new Table(title);
        table.addRow(providers.stream().map(ColumnProvider::getTitle).toList());
        for (var service : elements) {
            table.addRow(providers.stream().map(provider -> provider.extract(service)).toList());
        }
    }

    @Override
    public Table currentState() {
        return table;
    }

    private Table table;
    private final String title;
    private final List<ColumnProvider> providers;
}
