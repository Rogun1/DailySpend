import { useState } from 'react'
import { login } from '../services/api'

function Login({ onLogin }) {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')

  async function handleLogin() {
    try {
      setError('')

      await login(email, password)

      onLogin()
    } catch (error) {
      console.error(error)
      setError(error.message)
    }
  }

  return (
    <div>
      <h1>DailySpend</h1>
      <h2>Login</h2>

      <input
        type="email"
        placeholder="Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />

      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />

      <button onClick={handleLogin}>
        Login
      </button>

      {error && <p>{error}</p>}
    </div>
  )
}

export default Login