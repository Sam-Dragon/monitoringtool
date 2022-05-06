package learn.monitoring.tool.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Metrics;

@RestController
@RequestMapping("/metrics")
public class HelloController {

	static final Counter counter = Counter.builder("cisco_total_count").tag("cisco_total_count", "Total number of requests")
			.register(Metrics.globalRegistry);

	static final Gauge guage = Gauge.builder("cisco_total_thread", () -> 1).strongReference(true)
			.tag("cisco_total_thread", "Total number of threads").register(Metrics.globalRegistry);

	@GetMapping("/counter")
	public String counterMethod() {
		counter.increment();
		System.out.println("Total Hits :: " + counter.count());
		return "counter";
	}

	@GetMapping("/gauge")
	public String gaugeMethod() {
		guage.value();
		System.out.println("Total Threads :: " + guage.value());
		return "gauge";
	}

	@Timed(value = "cisco_timer", description = "Api Timer", histogram = true, percentiles = 0.95)
	@GetMapping("/timer")
	public String timerMethod() {
		System.out.println("Timer :: ");
		return "timer";
	}
}
