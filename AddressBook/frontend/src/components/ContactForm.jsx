import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const ContactForm = ({ initialData, onSubmit, isSubmitting }) => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    address: '',
    city: '',
    state: '',
    zip: '',
    phoneNumber: '',
    email: '',
    ...initialData
  });

  useEffect(() => {
    if (initialData) {
      setFormData(initialData);
    }
  }, [initialData]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Use HTML5 validation inherently by required attributes
    // Also, zip validation to 5/6 digits and phone to valid format could be added here
    // but the prompt mentioned HTML5 validation is fine unless specified.
    const newContact = {
      ...formData,
      // Provide a random ID if adding, or keep existing. json-server generates IDs automatically for POST
      // if not provided, but we don't have to provide one unless it's a PUT
      dateAdded: formData.dateAdded || new Date().toISOString().split('T')[0]
    };
    onSubmit(newContact);
  };

  return (
    <form onSubmit={handleSubmit} className="card form-container">
      <div className="form-row">
        <div className="form-group">
          <label className="form-label" htmlFor="firstName">First Name</label>
          <input type="text" id="firstName" name="firstName" className="form-control" value={formData.firstName} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label className="form-label" htmlFor="lastName">Last Name</label>
          <input type="text" id="lastName" name="lastName" className="form-control" value={formData.lastName} onChange={handleChange} required />
        </div>
      </div>

      <div className="form-group">
        <label className="form-label" htmlFor="address">Address</label>
        <input type="text" id="address" name="address" className="form-control" value={formData.address} onChange={handleChange} required />
      </div>

      <div className="form-row">
        <div className="form-group">
          <label className="form-label" htmlFor="city">City</label>
          <input type="text" id="city" name="city" className="form-control" value={formData.city} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label className="form-label" htmlFor="state">State</label>
          <input type="text" id="state" name="state" className="form-control" value={formData.state} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label className="form-label" htmlFor="zip">ZIP</label>
          <input type="text" id="zip" name="zip" className="form-control" value={formData.zip} onChange={handleChange} required pattern="[0-9]{5,6}" title="5 or 6 digit ZIP code" />
        </div>
      </div>

      <div className="form-row">
        <div className="form-group">
          <label className="form-label" htmlFor="phoneNumber">Phone Number</label>
          <input type="tel" id="phoneNumber" name="phoneNumber" className="form-control" value={formData.phoneNumber} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label className="form-label" htmlFor="email">Email</label>
          <input type="email" id="email" name="email" className="form-control" value={formData.email} onChange={handleChange} required />
        </div>
      </div>

      <div className="flex justify-end gap-4 mt-6">
        <button type="button" className="btn btn-icon" onClick={() => navigate('/')} style={{ padding: '0.6rem 1.2rem', borderRadius: '8px', background: 'var(--card-bg)' }}>Cancel</button>
        <button type="submit" className="btn btn-primary" disabled={isSubmitting}>
          {isSubmitting ? 'Saving...' : 'Save Contact'}
        </button>
      </div>
    </form>
  );
};

export default ContactForm;
