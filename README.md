# ARA: RESTful APIs
RESTful APIs for a ride-sharing platform implemented with Spark Framwork and Morphia

`gradle run` and test on `http://localhost:8080/v1/car`

### Error Codes ###
| Error Code | Error Message                            | Relevant Resources | Parameters |
| ---------- | ---------------------------------------- | ------------------ | :--------- |
| 1001       | Invalid resource name                    | All Resources      | None       |
| 1002       | Given car does not exist                 | `cars`             | None       |
| 1003       | Given driver does not exist              | `drivers`          | None       |
| 1004       | Given passenger does not exist           | `passengers`       | None       |
| 1005       | Given ride does not exist                | `ride`             | None       |
| 2000       | Invalid data type                        | All Resources      | None       |
| 3000       | Email address already taken              | `passenger`, `driver` | None       |
| 5000       | Exception                                | All Resources      | None       |
| 9001       | Authentication failed. Wrong username or password | None               | None       |
| 9002       | Failed to authenticate token             | All Resources      | None       |
| 9003       | No token provided                        | All Resources      | None       |
