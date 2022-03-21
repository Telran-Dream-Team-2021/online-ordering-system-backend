## PRODUCTS ProductController /products
### GET /
	### Success
	200: [
		{
			id: number,
			name: string,
			categoryName: string,
			description: string,
			imageUrl: string,
			price: number,
			unitOfMeasurement: string,
			isActive: boolean,
		}
		...
	]

	### Not Found
	404
	
### GET /{id}
	### Success
	200: {
		id: number,
		name: string,
		categoryName: string,
		description: string,
		imageUrl: string,
		price: number,
		unitOfMeasurement: string,
		isActive: boolean,
	}

	### Not Found
	404

### DELETE /{id}
	### Success
	204
	
	### Unauthorized
	401
	
	### Forbidden
	403

	### Not Found
	404

### POST /
### PUT  /{id}
	body: {
		name: string,
		categoryName: string,
		description: string,
		imageUrl: string,
		price: number,
		unitOfMeasurement: string,
		isActive: boolean,
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
