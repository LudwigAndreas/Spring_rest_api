# EternalTimeline API Documentation

## Introduction [introduction]

This document provides an overview and documentation for the LifeTracker REST API service. It describes the available endpoints, request and response formats, and provides examples for common use cases, security configuration and token usage.

## Table of content [table-of-content]
- [Base URL](#base-url)
- [Authentication](#authentication)
	- [signup](#signup-endpoint)
	-	[login](#login-endpoint)
	- [logout](#logout-endpoint)
	- [logout-all](#logout-all-endpoint)
	- [access-token](#access-token-endpoint)
	- [refresh-token](#refresh-token-endpoint)
- [Endpoints](#endpoints)
	- [User](#user-endpoints)
		- [delete](#user-delete-endpoint)
		- [get](#user-get-endpoint)
		- [patch](#user-patch-endpoint)
		- [update](#user-update-endpoint)
	- [Workflow](#workflow-endpoints)
		- [delete](#workflow-delete-endpoint)
		- [get](#workflow-get-endpoint)
		- [insert](#workflow-insert-endpoint)
		- [list](#workflow-list-endpoint)
		- [patch](#workflow-patch-endpoint)
		- [update](#workflow-update-endpoint)
	- [Project](#project-endpoints)
		- [delete](#project-delete-endpoint)
		- [get](#project-get-endpoint)
		- [insert](#project-insert-endpoint)
		- [list](#project-list-endpoint)
		- [patch](#project-patch-endpoint)
		- [update](#project-update-endpoint)
	- [Event](#event-endpoints)
		- [delete](#event-delete-endpoint)
		- [get](#event-get-endpoint)
		- [import](#event-import-endpoint)
		- [insert](#event-insert-endpoint)
		- [instances](#event-instances-endpoint)
		- [list](#event-list-endpoint)
		- [move](#event-move-endpoint)
		- [patch](#event-patch-endpoint)
		- [update](#event-update-endpoint)
- [Error Handling](#error-handling)
- [Conclusion](#conclusion)

## Base URL [base-url]

The base URL for accessing the API service is:
`https://eternaltimeline.ru/api`

## Authentication [authentication]

To access the API service, you need to include an authentication token in the request header. The token should be included in the `Authorization` header field using the `Bearer` authentication scheme.

If you are a new user, you need to send a request to "", after which you will get your UserId AccessToken and RefreshToken.

### `POST /auth/signup` [signup-endpoint]
Endpoint allows you to create a new user. It requires the following parameters:

- `username`: \[string](required) unique username
- `email`: \[string](required) unique email
- `password`: \[string](required) password
- `firstname`: \[string](optional) firstname
- `lastname`: \[string](optional) lastname

#### Request 
```http
POST /signup HTTP/1.1
Content-Type: application/json

{
  "username": "YourUsername",
	"email": "Example@yahoo.com",
	"firstname": "ludwig",
	"firstname": "andreas",
	"password": "YourPassword123"
}
```

#### Response 
```json
{
    "user_id": "1234a56-789b-01cd-d23e-4567f8j9h0i1",
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuYXV0aDAuY29tLyIsImF1ZCI6Imh0dHBzOi8vYXBpLmV4YW1wbGUuY29tL2NhbGFuZGFyL3YxLyIsInN1YiI6InVzcl8xMjMiLCJpYXQiOjE0NTg3ODU3OTYsImV4cCI6MTQ1ODg3MjE5Nn0.CA7eaHjIHz5NxeIJoFK9krqaeZrPLwmMmgI_XiQiIkQ",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuYXV0aDAuY29tLyIsImF1ZCI6Imh0dHBzOi8vYXBpLmV4YW1wbGUuY29tL2NhbGFuZGFyL3YxLyIsInN1YiI6InVzcl8xMjMiLCJpYXQiOjE0NTg3ODU3OTYsImV4cCI6MTQ1ODg3MjE5Nn0.CA7eaHjIHz5NxeIJoFK9krqaeZrPLwmMmgI_XiQiIkQ"
}
```

If you already have an account, you can use the login form

### `POST /auth/login` [login-endpoint]
Endpoint allows you to login. It requires the following parameters:

- `username`: \[string](required) your username
- `password`: \[string](required) password

#### Request 
```http
POST /login HTTP/1.1
Content-Type: application/json

{
  "username": "YourUsername",
	"password": "YourPassword123"
}
```

#### Response 
```json
{
    "user_id": "1234a56-789b-01cd-d23e-4567f8j9h0i1",
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuYXV0aDAuY29tLyIsImF1ZCI6Imh0dHBzOi8vYXBpLmV4YW1wbGUuY29tL2NhbGFuZGFyL3YxLyIsInN1YiI6InVzcl8xMjMiLCJpYXQiOjE0NTg3ODU3OTYsImV4cCI6MTQ1ODg3MjE5Nn0.CA7eaHjIHz5NxeIJoFK9krqaeZrPLwmMmgI_XiQiIkQ",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuYXV0aDAuY29tLyIsImF1ZCI6Imh0dHBzOi8vYXBpLmV4YW1wbGUuY29tL2NhbGFuZGFyL3YxLyIsInN1YiI6InVzcl8xMjMiLCJpYXQiOjE0NTg3ODU3OTYsImV4cCI6MTQ1ODg3MjE5Nn0.CA7eaHjIHz5NxeIJoFK9krqaeZrPLwmMmgI_XiQiIkQ"
}
```

With each authorization, a new refreshToken is created, which you can use to access resources.

If you need to log out you can use next endpoint:

### `POST /auth/logout` [logout-endpoint]
Endpoint allows you to logout. It requires the following parameters:

- `user_id`: \[string](required) your user_id
- `access_token`: \[string](required) your access token
- `refresh_token`: \[string](required) your refresh token

#### Request 
```http
POST /logout HTTP/1.1
Content-Type: application/json

{
    "user_id": "1234a56-789b-01cd-d23e-4567f8j9h0i1",
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuYXV0aDAuY29tLyIsImF1ZCI6Imh0dHBzOi8vYXBpLmV4YW1wbGUuY29tL2NhbGFuZGFyL3YxLyIsInN1YiI6InVzcl8xMjMiLCJpYXQiOjE0NTg3ODU3OTYsImV4cCI6MTQ1ODg3MjE5Nn0.CA7eaHjIHz5NxeIJoFK9krqaeZrPLwmMmgI_XiQiIkQ",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuYXV0aDAuY29tLyIsImF1ZCI6Imh0dHBzOi8vYXBpLmV4YW1wbGUuY29tL2NhbGFuZGFyL3YxLyIsInN1YiI6InVzcl8xMjMiLCJpYXQiOjE0NTg3ODU3OTYsImV4cCI6MTQ1ODg3MjE5Nn0.CA7eaHjIHz5NxeIJoFK9krqaeZrPLwmMmgI_XiQiIkQ"
}
```

#### Response 
```json

```

The query will result in an empty body and the execution status can be tracked via the response status

### `POST /auth/logout-all` [logout-all-endpoint]
Endpoint allows you to logout all your devices. It requires the following parameters:

- `user_id`: \[string](required) your user_id
- `access_token`: \[string](required) your access token
- `refresh_token`: \[string](required) your refresh token

#### Request 
```http
POST /logout-all HTTP/1.1
Content-Type: application/json

{
    "user_id": "1234a56-789b-01cd-d23e-4567f8j9h0i1",
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuYXV0aDAuY29tLyIsImF1ZCI6Imh0dHBzOi8vYXBpLmV4YW1wbGUuY29tL2NhbGFuZGFyL3YxLyIsInN1YiI6InVzcl8xMjMiLCJpYXQiOjE0NTg3ODU3OTYsImV4cCI6MTQ1ODg3MjE5Nn0.CA7eaHjIHz5NxeIJoFK9krqaeZrPLwmMmgI_XiQiIkQ",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuYXV0aDAuY29tLyIsImF1ZCI6Imh0dHBzOi8vYXBpLmV4YW1wbGUuY29tL2NhbGFuZGFyL3YxLyIsInN1YiI6InVzcl8xMjMiLCJpYXQiOjE0NTg3ODU3OTYsImV4cCI6MTQ1ODg3MjE5Nn0.CA7eaHjIHz5NxeIJoFK9krqaeZrPLwmMmgI_XiQiIkQ"
}
```

#### Response 
```json

```

Works just the same as `/logout` but logout all devices

### `POST /auth/access-token` [access-token-endpoint]
Generates new access-token using refresh-token

- `user_id`: \[string](required) your user_id
- `refresh_token`: \[string](required) your refresh token

#### Request 
```http
POST /access-token HTTP/1.1
Content-Type: application/json

{
    "user_id": "1234a56-789b-01cd-d23e-4567f8j9h0i1",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuYXV0aDAuY29tLyIsImF1ZCI6Imh0dHBzOi8vYXBpLmV4YW1wbGUuY29tL2NhbGFuZGFyL3YxLyIsInN1YiI6InVzcl8xMjMiLCJpYXQiOjE0NTg3ODU3OTYsImV4cCI6MTQ1ODg3MjE5Nn0.CA7eaHjIHz5NxeIJoFK9krqaeZrPLwmMmgI_XiQiIkQ"
}
```

#### Response 
```json
{
    "user_id": "1234a56-789b-01cd-d23e-4567f8j9h0i1",
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuYXV0aDAuY29tLyIsImF1ZCI6Imh0dHBzOi8vYXBpLmV4YW1wbGUuY29tL2NhbGFuZGFyL3YxLyIsInN1YiI6InVzcl8xMjMiLCJpYXQiOjE0NTg3ODU3OTYsImV4cCI6MTQ1ODg3MjE5Nn0.CA7eaHjIHz5NxeIJoFK9krqaeZrPLwmMmgI_XiQiIkQ",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuYXV0aDAuY29tLyIsImF1ZCI6Imh0dHBzOi8vYXBpLmV4YW1wbGUuY29tL2NhbGFuZGFyL3YxLyIsInN1YiI6InVzcl8xMjMiLCJpYXQiOjE0NTg3ODU3OTYsImV4cCI6MTQ1ODg3MjE5Nn0.CA7eaHjIHz5NxeIJoFK9krqaeZrPLwmMmgI_XiQiIkQ"
}
```

### `POST /auth/refresh-token` [refresh-token-endpoint]
Generates new access-token and refresh-token using refresh-token

- `user_id`: \[string](required) your user_id
- `refresh_token`: \[string](required) your refresh token

#### Request 
```http
POST /logout HTTP/1.1
Content-Type: application/json

{
    "user_id": "1234a56-789b-01cd-d23e-4567f8j9h0i1",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuYXV0aDAuY29tLyIsImF1ZCI6Imh0dHBzOi8vYXBpLmV4YW1wbGUuY29tL2NhbGFuZGFyL3YxLyIsInN1YiI6InVzcl8xMjMiLCJpYXQiOjE0NTg3ODU3OTYsImV4cCI6MTQ1ODg3MjE5Nn0.CA7eaHjIHz5NxeIJoFK9krqaeZrPLwmMmgI_XiQiIkQ"
}
```

#### Response 
```json
{
    "user_id": "1234a56-789b-01cd-d23e-4567f8j9h0i1",
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuYXV0aDAuY29tLyIsImF1ZCI6Imh0dHBzOi8vYXBpLmV4YW1wbGUuY29tL2NhbGFuZGFyL3YxLyIsInN1YiI6InVzcl8xMjMiLCJpYXQiOjE0NTg3ODU3OTYsImV4cCI6MTQ1ODg3MjE5Nn0.CA7eaHjIHz5NxeIJoFK9krqaeZrPLwmMmgI_XiQiIkQ",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuYXV0aDAuY29tLyIsImF1ZCI6Imh0dHBzOi8vYXBpLmV4YW1wbGUuY29tL2NhbGFuZGFyL3YxLyIsInN1YiI6InVzcl8xMjMiLCJpYXQiOjE0NTg3ODU3OTYsImV4cCI6MTQ1ODg3MjE5Nn0.CA7eaHjIHz5NxeIJoFK9krqaeZrPLwmMmgI_XiQiIkQ"
}
```



## Endpoints [endpoints]

### User [user-endpoints]
User contains all info about user.


#### `DELETE /users/{userId}` [user-delete-endpoint]

/users allows you to delete all user data. It supports the following parameters:

- `userId`: (required) userId as it's provided after authentication.

##### Request

```http
DELETE /users/{id} HTTP/1.1
Authorization: Bearer <your_token>
```

##### Response
```json
{
    "id": "1234a56-789b-01cd-d23e-4567f8j9h0i1"
}
```

#### `GET /users/{id}` [user-get-endpoint]

/users allows you to retrieve a user data. It supports the following parameters:

- `userId`: (required) userId as it's provided after authentication.

##### Request

```http
GET /users/{id} HTTP/1.1
Authorization: Bearer <your_token>
```

##### Response
```json
{
    "id": "1234a56-789b-01cd-d23e-4567f8j9h0i1",
    "username": "lsherry",
    "firstname": "Ludwig",
    "lastname": "Andreas",
    "email": "landreas@gmail.com",
    "created_date": "2023-05-18",
    "last_updated_date": "2023-05-18"
}
```

#### `PATCH /users/{id}` [user-patch-endpoint]

/users allows you to change a user data. Supports JSON Patch that specified in RFC 6902 from the IETF.

- `userId`: (required) userId as it's provided after authentication.

##### Request

```http
GET /users/{id} HTTP/1.1
Authorization: Bearer <your_token>

[
  { "op": "replace", "path": "/username", "value": "lsherry_new" },
  { "op": "replace", "path": "/firstname", "value": "Restalw" },
  { "op": "replace", "path": "/email", "value": "wlaster@gmail.com" }
]
```

##### Response
```json
{
    "id": "1234a56-789b-01cd-d23e-4567f8j9h0i1",
    "username": "lsherry_new",
    "firstname": "Restalw",
    "lastname": "Andreas",
    "email": "wlaster@gmail.com",
    "created_date": "2023-05-18",
    "last_updated_date": "2023-05-18"
}
```

#### `PUT /users/{id}` [user-update-endpoint]

/users allows you to change whole user data. It supports the following parameters:

- `userId`: (required) userId as it's provided after authentication.
- `username`: (optional) userId as it's provided after authentication.
- `firstname`: (optional) userId as it's provided after authentication.
- `lastname`: (optional) userId as it's provided after authentication.
- `email`: (optional) userId as it's provided after authentication.
- ~~`password`: (optional) userId as it's provided after authentication.~~

##### Request

```http
GET /users/{id} HTTP/1.1
Authorization: Bearer <your_token>

{
    "id": "1234a56-789b-01cd-d23e-4567f8j9h0i1",
    "username": "lsherry_new",
    "firstname": "Restalw",
    "lastname": "Andreas",
    "email": "wlaster@gmail.com",
    "created_date": "2023-05-18",
    "last_updated_date": "2023-05-18"
}
```

##### Response
```json
{
    "id": "1234a56-789b-01cd-d23e-4567f8j9h0i1",
    "username": "lsherry_new",
    "firstname": "Restalw",
    "lastname": "Andreas",
    "email": "wlaster@gmail.com",
    "created_date": "2023-05-18",
    "last_updated_date": "2023-05-18"
}
```


### Workflow

Documentation not provided yet

### Project

Documentation not provided yet

### Event

Documentation not provided yet

## Error Handling
In case of errors, the API service returns appropriate HTTP status codes along with error messages in the response body.

Here are some common error codes:

`400 Bad Request`: The request was invalid or could not be understood.
`401 Unauthorized`: The request lacks valid authentication credentials.
`404 Not Found`: The requested resource could not be found.
`409 Conflict`: The request can not be performed because of conflict of user's data.

## Conclusion
This concludes the documentation for the REST API service. Please refer to the specific endpoint details for more information on how to use the API.

If you have any further questions or need assistance, please contact our support team.




