
Report-Gen
==========

Playing with combining Flying Saucer, Mapbox and XCharts to create reports.

To generate the Wildfire pdf report:

```java
  MapBuilder mapBuilder = new MapBuilder(MapboxApiKey.get());

  TemplateBuilder<FireData> templateBuilder = TemplateBuilder.withTemplate(
                                                                FIRE_REPORT_TYPE, 
                                                                classPathTemplateProvider("/fire.html.ftl"),
                                                                new FireTemplateProvider(mapBuilder));

  ReportGenerator<FireData> generator = ReportGenerator.withTemplateBuilder(templateBuilder)
                                                         .withReplacer(new ReplaceWithMap<>())
                                                         .withReplacer(new ReplaceWithChart<>(new FireChartMaker()));

  generator.generate(FIRE_REPORT_TYPE, new FireData(), new File("fire.pdf"));
```

The FireTemplateProvider provides a model that gets passed to the freemarker template. 
Certain elements in the freemarker template can be replaced by either map or chart images.

The MapBuilder builds urls that are compliant with the mapbox static api:

```java
  String mapUrl = mapBuilder.newMap(MapType.SATELLITE, context.getSuspectedSource())
                            .zoomLevel(15)
                            .addMarker(context.getPoints().get(1), BLUE, "2")
                            .addMarker(context.getPoints().get(2), GREEN, "3")
                            .addPath(context.getFireLine(), RED)
                            .addArrow(context.getSuspectedSource(), context.getWindBearing(), WHITE, BLACK)
                            .buildUrl();
```

A sample report can be seen here: 
