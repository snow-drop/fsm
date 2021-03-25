`com.hicx.fsm.extraction.FileContentExtractorService`

* an interface responsible for extracting the text contents of a file (txt, csv, pdf, etc)

<br/><br/>
`com.hicx.fsm.extraction.FileContentExtractServiceImpl`

* a concrete implementation of `FileContentExtractorService` that holds the concrete logic on how to extract the text
  contents of a file. Currently, the implementation supports only files with MIME type of `text/plain` but should also
  work with `text/csv` or any file that can be opened as plain text and is not encoded as binary file.
  <br/><br/>
* has a method named `getText(String filePath)` that accepts the path of the file to extract the text contents and
  return the text contents as `String.`

<br/><br/>
`com.hicx.fsm.statistics.FileStatistics`

* a POJO that holds the statistics about a file. Currently, it holds the file name, the number of words, the number of
  dots, and the most used word(s) in a file.

<br/><br/>
`com.hicx.fsm.statistics.FileStatisticsService`

* an interface responsible for generating the statistics about the file (e.g. number of words, number of special
  characters, etc.)

<br/><br/>
`com.hicx.fsm.statistics.FileStatiticsServiceImpl`

* a concrete implementation of `FileStatisticsService.` This class contains the logic on the inner workings on how to
  generate the statistics of a file. It accepts a `FileContentExtractorService` as an argument on its constructor.
  `FileContentExtractorService` will first extract the contents of the file as a `String` and will hand it over to
  `FileStatisticsServiceImpl` to process.
  <br/><br/>
* has a method `getStatistics(String filePath)` that accepts the path of the file to generate statistics to. This method
  will handle the generation of statistics by counting the number of words on the file, counting the number of dots, and
  retrieving the words that are mostly used in the text contents. This will return a `FileStatistics` object that
  contains all the statistics about the file.

<br/><br/>
`com.hicx.monitoring.FileMonitoringService`

* an interface that will handle the monitoring of files in a directory and processing existing and new files in that
  directory.

<br/><br/>
`com.hicx.monitoring.FileMonitoringServiceImpl`

* this class implements `FileMonitoringService` and contains the concrete implementation of `FileMonitoringService.`
  The class accepts a `FileStatisticsService` as an argument to its constructor for generating statistics on existing
  and newly created files on the monitored directory.
  <br/><br/>
* has a method `monitor(String directoryPath)` that accepts the path of the folder to monitor. This method will first
  process existing files on that directory and will then proceed to monitoring new files created on that folder. This
  method will block until a new file is created on that directory. When a new file is added on the monitored folder, it
  will first generate a statistics on that file, printing the statistics on the console, and will then move this file
  into a sub-folder named `processed` to indicate that the file was already processed for statistics generation.

<br/><br/>
`com.hicx.FileStatisticsMonitoringSystem`

* this class contains the `main` method that will start the application. This class is responsible for creation of the
  concrete implementation of the services and will inject them on the constructors when needed. The `main` method
  accepts the directory to monitor as an argument when running the executable jar and will start the monitoring on this
  folder.