import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { fetchContacts } from '../store/contactsSlice';
import ContactCard from '../components/ContactCard';

const Dashboard = () => {
  const dispatch = useDispatch();
  const { items, status, error } = useSelector((state) => state.contacts);

  useEffect(() => {
    if (status === 'idle') {
      dispatch(fetchContacts());
    }
  }, [status, dispatch]);

  if (status === 'loading') {
    return <div className="state-message">Loading contacts...</div>;
  }

  if (status === 'failed') {
    return <div className="state-message" style={{ color: 'var(--danger)' }}>Error: {error}</div>;
  }

  if (items.length === 0) {
    return (
      <div className="state-message">
        <h2>No contacts found.</h2>
        <p>Click "Add Contact" to create your first contact.</p>
      </div>
    );
  }

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h1 style={{ fontSize: '2rem', fontWeight: 'bold' }}>All Contacts</h1>
      </div>
      <div className="contact-grid">
        {items.map((contact) => (
          <ContactCard key={contact.id} contact={contact} />
        ))}
      </div>
    </div>
  );
};

export default Dashboard;
