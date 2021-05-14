## PerformanceAppraisal-MS-cloudant
Performance Appraisal MS using IBM Cloudant

### Overview
This REST API is built on the following assumptions:
* the employee id provided in the request is a valid document id used to create an employee document in the employee database.
  e.g. employee document id "f03b0dfeefcdfd0f55db95820e4f55a1"

The following requests are supported where the id is the employee id:
* `GET` /getPerfAppraisalByEmployeeId/{id}
* `POST` /savePerfAppraisal
* `POST` /updatePerfAppraisal
* `DELETE` /deletePerfAppraisalByEmployeeId/{id}

### Sample API requests

Sample `savePerfAppraisal` request:
```
{
    "empId": "a1b8f8bbdd733dd9afc5e2017aa39e53",
    "rating": "Meets Expectation",
    "feedback": "On time when the visitors arrive"
}
```

Sample `updatePerfAppraisal` request:
```
{
  "empId": "f03b0dfeefcdfd0f55db95820e4f55a1",
  "rating": "Below Expectation",
  "feedback": "Visitors are turned off by his wise guy attitude"
}
```
### Sample API responses

Sample responses for the `getPerfAppraisalByEmployeeId` request:
```
{
    "id": "8e6c3c24501f4972b1a93370a82e80a2",
    "revision": "1-957f7b6b5c5da64f8483505e05eb1cca",
    "attachments": null,
    "deleted": false,
    "empId": "a1b8f8bbdd733dd9afc5e2017aa39e53",
    "rating": "Meets Expectation",
    "feedback": "On time when the visitors arrive"
}

{
    "id": null,
    "revision": null,
    "attachments": null,
    "deleted": false,
    "empId": "No performance appraisal was found for employee id",
    "rating": "N/A",
    "feedback": "N/A"
}
```

Sample responses for the `savePerfAppraisal` request:
```
"SUCCESS"
"FAILURE|Employee id was not provided in the request"
"FALIURE|[exception message]"
"FALIURE|Performance appraisal already exists for employee id"
```

Sample responses for the `updatePerfAppraisal` request:
```
"SUCCESS"
"FAILURE|Employee id was not provided in the request"
"FAILURE|No performance appraisal was found for employee id"
"FAILURE|Multiple performance appraisals were found for employee id"
```

Sample responses for the `deletePerfAppraisalByEmployeeId` request:
```
"SUCCESS"
"FAILURE|No performance appraisal was found for employee id"
"FAILURE|Multiple performance appraisals were found for employee id"
```