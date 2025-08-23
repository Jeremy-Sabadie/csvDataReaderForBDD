# Spring Batch CSV Import Demo

This project is a **training exercise** designed to get familiar with **Spring Batch** before contributing to a real-world professional project in the company.  

The application demonstrates how to:  
- Build a **Spring Boot + Spring Batch** job.  
- Read data from a **CSV file** using a `FlatFileItemReader`.  
- Map each line into a `Person` entity.  
- Process and clean data using a custom `ItemProcessor`.  
- Persist valid records into a **MariaDB database (running inside a Docker container)** with `JpaItemWriter`.  
- Handle duplicates safely with a **custom SkipPolicy** (`DuplicateEmailSkipPolicy`) and a `SkipListener`.  
- Use Spring Batch **metadata tables (`BATCH_JOB_*`)** to track job executions.  

---

## Key Learning Outcomes
- Understand the architecture of a **Spring Batch job** (Job → Step → Reader → Processor → Writer).  
- Configure **database access and job schema initialization** via `application.properties`.  
- Learn how to **filter, normalize, and validate data** before persistence.  
- Experiment with **error handling** (skip duplicate entries, log skipped records).  
- Run and re-run jobs while keeping a history of execution.  

---

## Purpose
This repository is **not a production-ready application**, but a **hands-on onboarding exercise**.  
The goal is to practice the main concepts of **ETL pipelines with Spring Batch**, and to run them against a **MariaDB database container** in order to simulate a real-world enterprise environment.  

---
