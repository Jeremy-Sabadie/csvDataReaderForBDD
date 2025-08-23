package config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test") // pas pendant les tests
public class RunJobOnStartup implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final Job importPersonJob;

    public RunJobOnStartup(JobLauncher jobLauncher, Job importPersonJob) {
        this.jobLauncher = jobLauncher;
        this.importPersonJob = importPersonJob;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("ts", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(importPersonJob, params);
    }
}
