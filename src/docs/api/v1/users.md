## USER UsersController /users
### GET / (admin only)
	### Success
	200: [
		{
			uid: number;//id
			isAdmin: boolean;
			displayName: string;
			deliveryAddress?: string;
			email: string;
		}
		...
	]

	### Unauthorized
	401
	
	### Forbidden
	403

	### Not Found
	404
	
### GET /{id}
	### Success
	200: {
			username: number;//id
			isAdmin: boolean;
			displayName: string;
			deliveryAddress?: string;
			email: string;
		}

	### Unauthorized
	401
	
	### Forbidden
	403

	### Not Found
	404

### DELETE /{id}
	405 Method Not Allowed

### POST /
### PUT  /{id}
	body: {
		username: number;//id
		isAdmin: boolean;
		displayName: string;
		deliveryAddress: string;
		email: string;
	}
	
	### Success
	204
	
	### Unauthorized
	401
	
	### Forbidden
	403
	
	### Validation errors
	422 Unprocessable Entity

	### Bad Request
	400	

	### Not Found
	404