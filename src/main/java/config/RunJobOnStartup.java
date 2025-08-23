package config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
@Profile("!test")
public class RunJobOnStartup implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    @Value("${spring.batch.job.name:importPersonJob}")
    private String jobName; // default = importPersonJob

    public RunJobOnStartup(JobLauncher jobLauncher, JobRegistry jobRegistry) {
        this.jobLauncher = jobLauncher;
        this.jobRegistry = jobRegistry;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Job job = jobRegistry.getJob(jobName);
        JobParameters params = new JobParametersBuilder()
                .addLong("ts", System.currentTimeMillis(), true)
                .toJobParameters();
        jobLauncher.run(job, params);
    }
}
