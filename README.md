# Token Server API

## Overview
The **Token Server API** issues and validates **JWT access tokens** using **RSA signatures**, following the **OAuth2 Client Credentials Flow**.  
It is intended to be used by trusted clients to securely obtain access tokens for consuming protected services.

---

## Security: Public/Private Key Communication 🔐
This server relies on **asymmetric cryptography (RSA)**:

- **Client** signs a JWT with its **private key**.
- **Server** verifies the JWT using the corresponding **public key** stored in the database.
- This ensures that:
    1. Only clients with a valid private key can generate valid tokens.
    2. The server never needs to know the client's private key.
    3. JWT integrity and authenticity are guaranteed.

**In English:**
> The client uses a private key to "sign" the token, proving it is authentic.  
> The server uses the public key to verify the signature without knowing the private key.  
> This ensures secure communication between client and server.

---

---

## 🔁 `/token-classic` – Body-based Client Assertion Flow (RFC 7523)

This endpoint behaves like `/token` but receives the **client assertion** (a signed JWT) in the request body instead of the Authorization header.

This follows the [OAuth2 JWT Bearer Token Flow (RFC 7523)](https://datatracker.ietf.org/doc/html/rfc7523), allowing clients to authenticate using a signed assertion directly in the body.

### Example Request

```http
POST /token-g
Content-Type: application/json

{
  "client_id": "123e4567-e89b-12d3-a456-426614174000",
  "client_assertion": "eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9..."
}

