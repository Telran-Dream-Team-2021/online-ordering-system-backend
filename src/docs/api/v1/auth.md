## AUTH AuthController /auth

### GET /admins/check-email?email={email}
	### Success
	204

	### Not Found
	404

### GET /admins/{id}
	### Success
	204

	### Not Found
	404

### POST /signup??
### POST /signin
	body: {
		email: string,
		password?: string, // if not passed, sends an email with link for enter
		provider?: string  // if passed - OAUTH
	}

	### if with password
	200: {
		username: number,
		displayName: string,
		isAdmin: boolean,
		email: string,
	} 
	
	### if with email
	204
	
	### Not Found
	404
	
	
### GET /signin/bylink?email={email}&token={token}
	### Success
	200: {
		username: number,
		displayName: string,
		isAdmin: boolean,
		email: string,
	} 
	
	### Not Found
	404

### POST /signout
	body: {token: {token}}
	
	### Success
	204
	
	### Not Found
	404

