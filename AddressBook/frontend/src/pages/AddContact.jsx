import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { addContact } from '../store/contactsSlice';
import ContactForm from '../components/ContactForm';

const AddContact = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = async (contactData) => {
    setIsSubmitting(true);
    try {
      await dispatch(addContact(contactData)).unwrap();
      navigate('/');
    } catch (err) {
      console.error('Failed to add contact: ', err);
      alert('Failed to save contact.');
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div>
      <h1 className="mb-6" style={{ fontSize: '2rem', fontWeight: 'bold', textAlign: 'center' }}>Add New Contact</h1>
      <ContactForm onSubmit={handleSubmit} isSubmitting={isSubmitting} />
    </div>
  );
};

export default AddContact;
