#!/bin/bash

# Define your base URL
BASE_URL="http://localhost:8080"

# Define UUIDs for course and university (you can replace these with your actual values)
COURSE_ID="393872f8-8080-4316-b3f0-b5d93e86818b"
UNIVERSITY_ID="23feacae-1f9b-4bf2-8ace-c447c6f268c4"

# Perform a GET request to compare age average
curl -X GET "$BASE_URL/courses/compare-age-average/$COURSE_ID/$UNIVERSITY_ID"

# Define data for the POST request
ENROLL_DATA='{"studentId": "12345", "courseId": "67890"}'

# Perform a POST request to enroll a student in a course
curl -X POST "$BASE_URL/university/api/v1/students/enroll-in-course" \
-H "Content-Type: application/json" \
-d "$ENROLL_DATA"

# Define page number and page size for the GET request
PAGE_NUMBER=0
PAGE_SIZE=2

# Perform a GET request to retrieve a list of programs
curl -X GET "$BASE_URL/university/api/v1/programs/$PAGE_NUMBER/$PAGE_SIZE"

# Indicate script completion
echo "API calls completed."