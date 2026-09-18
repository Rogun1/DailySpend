import { useState } from 'react'
import { register } from '../services/api'

function Register() {
  const [form, setForm] = useState({
    firstName: '',
    lastName: '',
    email: '',
    pwd: '',
    age: '',
  })

  const [message, setMessage] = useState('')
  const [error, setError] = useState('')

  function handleChange(e) {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    })
  }

  async function handleRegister() {
    try {
      setError('')
      setMessage('')

      await register({
        ...form,
        age: Number(form.age),
      })

      setMessage('Account created successfully')
    } catch (error) {
      console.error(error)
      setError(error.message)
    }
  }

  return (
    <div>
      <h2>Create account</h2>

      <input
        name="firstName"
        placeholder="First name"
        value={form.firstName}
        onChange={handleChange}
      />

      <input
        name="lastName"
        placeholder="Last name"
        value={form.lastName}
        onChange={handleChange}
      />

      <input
        name="email"
        type="email"
        placeholder="Email"
        value={form.email}
        onChange={handleChange}
      />

      <input
        name="pwd"
        type="password"
        placeholder="Password"
        value={form.pwd}
        onChange={handleChange}
      />

      <input
        name="age"
        type="number"
        placeholder="Age"
        value={form.age}
        onChange={handleChange}
      />

      <button onClick={handleRegister}>
        Register
      </button>

      {message && <p>{message}</p>}
      {error && <p>{error}</p>}
    </div>
  )
}

export default Register