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

## Base URL
