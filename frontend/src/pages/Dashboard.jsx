import { useEffect, useState } from 'react'
import {
  getProfile,
  updateProfile,
  getSummary,
  getDailySpending,
  getSpendCategories,
  addSpend,
  addIncome,
  logout,
} from '../services/api'
import './Dashboard.css'

function Dashboard({ onLogout }) {
  const [activeSection, setActiveSection] = useState('summary')

  const [summaryDays, setSummaryDays] = useState(7)
  const [summary, setSummary] = useState(null)

  const [dailyDate, setDailyDate] = useState('')
  const [dailySpending, setDailySpending] = useState(null)

  const [categories, setCategories] = useState([])

    const [profile, setProfile] = useState({
      firstName: '',
      lastName: '',
      age: '',
    })

    const [profileEdit, setProfileEdit] = useState({
      firstName: '',
      lastName: '',
      age: '',
    })

  const [spend, setSpend] = useState({
    name: '',
    amount: '',
    quantity: '',
    category: '',
  })

  const [income, setIncome] = useState({
    name: '',
    amount: '',
  })

  const [message, setMessage] = useState('')
  const [error, setError] = useState('')

  useEffect(() => {
    async function loadCategories() {
      try {
        const data = await getSpendCategories()
        setCategories(data)
      } catch (error) {
        setError(error.message)
      }
    }

    loadCategories()
  }, [])

  useEffect(() => {
    if (activeSection !== 'profile') {
      return
    }

    async function loadProfile() {
      try {
        clearMessages()

        const data = await getProfile()

        setProfile({
          firstName: data.firstName,
          lastName: data.lastName,
          age: data.age,
        })

        setProfileEdit({
          firstName: '',
          lastName: '',
          age: '',
        })
      } catch (error) {
        setError(error.message)
      }
    }

    loadProfile()
  }, [activeSection])

  function clearMessages() {
    setMessage('')
    setError('')
  }

  function changeSection(section) {
    setActiveSection(section)
    clearMessages()
  }

  async function handleSummary() {
    try {
      clearMessages()
      const data = await getSummary(Number(summaryDays))
      setSummary(data)
    } catch (error) {
      setError(error.message)
    }
  }

  async function handleDailySpending() {
    try {
      clearMessages()
      const data = await getDailySpending(dailyDate)
      setDailySpending(data)
    } catch (error) {
      setError(error.message)
    }
  }

  async function handleAddSpend() {
    try {
      clearMessages()

      const data = await addSpend({
        name: spend.name,
        amount: Number(spend.amount),
        quantity: Number(spend.quantity),
        category: spend.category,
      })

      setMessage(data.response ?? 'Spend added successfully')
      setSpend({
        name: '',
        amount: '',
        quantity: '',
        category: '',
      })
    } catch (error) {
      setError(error.message)
    }
  }

  async function handleAddIncome() {
    try {
      clearMessages()

      const data = await addIncome({
        name: income.name,
        amount: Number(income.amount),
      })

      setMessage(data.response ?? 'Income added successfully')
      setIncome({
        name: '',
        amount: '',
      })
    } catch (error) {
      setError(error.message)
    }
  }

  async function handleUpdateProfile() {
    try {
      clearMessages()

      const data = await updateProfile({
        firstName: profileEdit.firstName,
        lastName: profileEdit.lastName,
        age: Number(profileEdit.age),
      })

      setProfile({
        firstName: data.firstName,
        lastName: data.lastName,
        age: data.age,
      })

      setProfileEdit({
        firstName: '',
        lastName: '',
        age: '',
      })

      setMessage('Profile updated successfully')
    } catch (error) {
      setError(error.message)
    }
  }

  function handleLogout() {
    logout()
    onLogout()
  }

  return (
    <div className="dashboard">
      <header className="dashboard-header">
        <div>
          <h1>DailySpend</h1>
          <p>Manage your finances</p>
        </div>

        <button className="logout-button" onClick={handleLogout}>
          Logout
        </button>
      </header>

      <nav className="dashboard-nav">
        <button
          className={activeSection === 'summary' ? 'active' : ''}
          onClick={() => changeSection('summary')}
        >
          Summary
        </button>

        <button
          className={activeSection === 'daily' ? 'active' : ''}
          onClick={() => changeSection('daily')}
        >
          Daily Spending
        </button>

        <button
          className={activeSection === 'spend' ? 'active' : ''}
          onClick={() => changeSection('spend')}
        >
          Add Spend
        </button>

        <button
          className={activeSection === 'income' ? 'active' : ''}
          onClick={() => changeSection('income')}
        >
          Add Income
        </button>

        <button
          className={activeSection === 'profile' ? 'active' : ''}
          onClick={() => changeSection('profile')}
        >
          Profile
        </button>
      </nav>

      <main className="dashboard-content">

        {activeSection === 'summary' && (
          <div className="dashboard-card">
            <div className="section-header">
              <h2>Summary</h2>
              <p>See your spending overview for the selected period.</p>
            </div>

            <div className="form-row">
              <div className="input-group">
                <label>Last days</label>

                <input
                  type="number"
                  min="1"
                  value={summaryDays}
                  onChange={(e) => setSummaryDays(e.target.value)}
                />
              </div>

              <button
                className="primary-button"
                onClick={handleSummary}
              >
                Get Summary
              </button>
            </div>

            {summary && (
              <div className="result-card">
                <div className="total">
                  <span>Total spending</span>
                  <strong>{summary.total}</strong>
                </div>

                <div className="categories">
                  <h3>Categories</h3>

                  {Object.entries(summary.categories).map(
                    ([category, amount]) => (
                      <div className="category-row" key={category}>
                        <span>{category}</span>
                        <strong>{amount}</strong>
                      </div>
                    )
                  )}
                </div>
              </div>
            )}
          </div>
        )}

        {activeSection === 'daily' && (
          <div className="dashboard-card">
            <div className="section-header">
              <h2>Daily Spending</h2>
              <p>Check everything you spent on a specific day.</p>
            </div>

            <div className="form-row">
              <div className="input-group">
                <label>Date</label>

                <input
                  type="date"
                  value={dailyDate}
                  onChange={(e) => setDailyDate(e.target.value)}
                />
              </div>

              <button
                className="primary-button"
                onClick={handleDailySpending}
              >
                Get Spending
              </button>
            </div>

            {dailySpending && (
              <div className="result-card">
                <div className="total">
                  <span>Total spending</span>
                  <strong>{dailySpending.total}</strong>
                </div>

                <div className="categories">
                  <h3>Spends</h3>

                  {dailySpending.spends.length === 0 ? (
                    <p className="empty-message">
                      No spending found for this date.
                    </p>
                  ) : (
                    dailySpending.spends.map((spend, index) => (
                      <div className="daily-spend" key={index}>
                        <div>
                          <strong>{spend.name}</strong>
                          <span> ({spend.category})</span>
                        </div>

                        <div>
                          {spend.amount} × {spend.quantity} ={' '}
                          <strong>{spend.totalAmount}</strong>
                        </div>
                      </div>
                    ))
                  )}
                </div>
              </div>
            )}
          </div>
        )}

        {activeSection === 'spend' && (
          <div className="dashboard-card">
            <div className="section-header">
              <h2>Add Spend</h2>
              <p>Add a new expense to your account.</p>
            </div>

            <div className="form-grid">
              <div className="input-group">
                <label>Name</label>

                <input
                  type="text"
                  placeholder="e.g. Groceries"
                  value={spend.name}
                  onChange={(e) =>
                    setSpend({
                      ...spend,
                      name: e.target.value,
                    })
                  }
                />
              </div>

              <div className="input-group">
                <label>Amount</label>

                <input
                  type="number"
                  step="0.01"
                  min="0"
                  placeholder="0.00"
                  value={spend.amount}
                  onChange={(e) =>
                    setSpend({
                      ...spend,
                      amount: e.target.value,
                    })
                  }
                />
              </div>

              <div className="input-group">
                <label>Quantity</label>

                <input
                  type="number"
                  min="1"
                  placeholder="1"
                  value={spend.quantity}
                  onChange={(e) =>
                    setSpend({
                      ...spend,
                      quantity: e.target.value,
                    })
                  }
                />
              </div>

              <div className="input-group">
                <label>Category</label>

                <select
                  value={spend.category}
                  onChange={(e) =>
                    setSpend({
                      ...spend,
                      category: e.target.value,
                    })
                  }
                >
                  <option value="">Select category</option>

                  {categories.map((category) => (
                    <option key={category} value={category}>
                      {category}
                    </option>
                  ))}
                </select>
              </div>
            </div>

            <button
              className="primary-button full-width"
              onClick={handleAddSpend}
            >
              Add Spend
            </button>
          </div>
        )}

        {activeSection === 'income' && (
          <div className="dashboard-card">
            <div className="section-header">
              <h2>Add Income</h2>
              <p>Add a new source of income to your account.</p>
            </div>

            <div className="form-grid">
              <div className="input-group">
                <label>Name</label>

                <input
                  type="text"
                  placeholder="e.g. Salary"
                  value={income.name}
                  onChange={(e) =>
                    setIncome({
                      ...income,
                      name: e.target.value,
                    })
                  }
                />
              </div>

              <div className="input-group">
                <label>Amount</label>

                <input
                  type="number"
                  step="0.01"
                  min="0"
                  placeholder="0.00"
                  value={income.amount}
                  onChange={(e) =>
                    setIncome({
                      ...income,
                      amount: e.target.value,
                    })
                  }
                />
              </div>
            </div>

            <button
              className="primary-button full-width"
              onClick={handleAddIncome}
            >
              Add Income
            </button>
          </div>
        )}

        {activeSection === 'profile' && (
          <div className="dashboard-card">
            <div className="section-header">
              <h2>Profile</h2>
              <p>View your account information and update your profile.</p>
            </div>

            <div className="result-card">
              <div className="section-header">
                <h3>Account Information</h3>
                <p>Your current account information.</p>
              </div>

              <div className="category-row">
                <span>First name</span>
                <strong>{profile.firstName}</strong>
              </div>

              <div className="category-row">
                <span>Last name</span>
                <strong>{profile.lastName}</strong>
              </div>

              <div className="category-row">
                <span>Age</span>
                <strong>{profile.age}</strong>
              </div>
            </div>

            <div className="result-card">
              <div className="section-header">
                <h3>Edit Profile</h3>
                <p>Change your personal information.</p>
              </div>

              <div className="form-grid">
                <div className="input-group">
                  <label>First name</label>

                  <input
                    type="text"
                    placeholder="First name"
                    value={profileEdit.firstName}
                    onChange={(e) =>
                      setProfileEdit({
                        ...profileEdit,
                        firstName: e.target.value,
                      })
                    }
                  />
                </div>

                <div className="input-group">
                  <label>Last name</label>

                  <input
                    type="text"
                    placeholder="Last name"
                    value={profileEdit.lastName}
                    onChange={(e) =>
                      setProfileEdit({
                        ...profileEdit,
                        lastName: e.target.value,
                      })
                    }
                  />
                </div>

                <div className="input-group">
                  <label>Age</label>

                  <input
                    type="number"
                    min="1"
                    placeholder="Age"
                    value={profileEdit.age}
                    onChange={(e) =>
                      setProfileEdit({
                        ...profileEdit,
                        age: e.target.value,
                      })
                    }
                  />
                </div>
              </div>

              <button
                className="primary-button full-width"
                onClick={handleUpdateProfile}
              >
                Save Changes
              </button>
            </div>
          </div>
        )}

        {message && (
          <div className="message success">
            {message}
          </div>
        )}

        {error && (
          <div className="message error">
            {error}
          </div>
        )}
      </main>
    </div>
  )
}

export default Dashboard