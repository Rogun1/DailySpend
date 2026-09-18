import { useState } from 'react'
import Login from './pages/Login'
import Register from './pages/Register'
import Dashboard from './pages/Dashboard'

function App() {
  const [authenticated, setAuthenticated] = useState(
    Boolean(localStorage.getItem('token'))
  )

  const [showRegister, setShowRegister] = useState(false)

  if (authenticated) {
    return (
      <Dashboard
        onLogout={() => setAuthenticated(false)}
      />
    )
  }

  if (showRegister) {
    return (
      <div>
        <Register />

        <button onClick={() => setShowRegister(false)}>
          Back to Login
        </button>
      </div>
    )
  }

  return (
    <div>
      <Login
        onLogin={() => setAuthenticated(true)}
      />

      <button onClick={() => setShowRegister(true)}>
        Create account
      </button>
    </div>
  )
}

export default App