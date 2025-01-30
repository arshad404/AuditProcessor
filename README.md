# Audit Processor

## Implementation Details

### Save the Request in the Context

Working:

1. Get the request
2. Transform the request
3. Save the request

Client -> Interceptor -> Encode -> Server -> Decode -> Interceptor -> Client

