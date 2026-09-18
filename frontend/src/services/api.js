const API_URL = 'http://localhost:8080'

async function apiRequest(endpoint, options = {}) {
  const token = localStorage.getItem('token')

  const response = await fetch(`${API_URL}${endpoint}`, {
    ...options,
    headers: {
      'Content-Type': 'application/json',
      ...(token && {
        Authorization: token,
      }),
      ...options.headers,
    },
  })

  if (!response.ok) {
    const errorText = await response.text()
    throw new Error(errorText || `Request failed: ${response.status}`)
  }

  return response.json()
}

export async function login(email, password) {
  const credentials = btoa(`${email}:${password}`)

  const response = await fetch(`${API_URL}/login`, {
    method: 'GET',
    headers: {
      Authorization: `Basic ${credentials}`,
    },
  })

  if (!response.ok) {
    throw new Error(`Login failed: ${response.status}`)
  }

  const token = response.headers.get('Authorization')

  if (!token) {
    throw new Error('JWT token not found')
  }

  localStorage.setItem('token', token)

  return token
}

export function register(account) {
  return apiRequest('/accounts', {
    method: 'POST',
    body: JSON.stringify(account),
  })
}

export function getSummary(days) {
  return apiRequest(`/accounts/spend/summary/${days}`)
}

export function getDailySpending(date) {
  return apiRequest(`/accounts/spend/daily?date=${date}`)
}

export function addSpend(spend) {
  return apiRequest('/accounts/spend/add', {
    method: 'POST',
    body: JSON.stringify(spend),
  })
}

export function addIncome(income) {
  return apiRequest('/accounts/income/add', {
    method: 'POST',
    body: JSON.stringify(income),
  })
}

export function logout() {
  localStorage.removeItem('token')
}

export function getSpendCategories() {
  return apiRequest('/accounts/spend/categories')
}