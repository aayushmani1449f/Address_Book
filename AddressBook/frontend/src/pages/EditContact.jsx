import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';
import { updateContact, fetchContacts } from '../store/contactsSlice';
import ContactForm from '../components/ContactForm';

const EditContact = () => {
  const { id } = useParams();
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [isSubmitting, setIsSubmitting] = useState(false);
  
  const { items, status } = useSelector(state => state.contacts);
  const contact = items.find(c => c.id.toString() === id);

  useEffect(() => {
    if (status === 'idle') {
      dispatch(fetchContacts());
    }
  }, [status, dispatch]);

  const handleSubmit = async (contactData) => {
    setIsSubmitting(true);
    try {
      await dispatch(updateContact({ id: contact.id, ...contactData })).unwrap();
      navigate('/');
    } catch (err) {
      console.error('Failed to update contact: ', err);
      alert('Failed to update contact.');
    } finally {
      setIsSubmitting(false);
    }
  };

  if (status === 'loading') return <div className="state-message">Loading...</div>;
  if (!contact && status === 'succeeded') return <div className="state-message text-danger">Contact not found!</div>;

  return (
    <div>
      <h1 className="mb-6" style={{ fontSize: '2rem', fontWeight: 'bold', textAlign: 'center' }}>Edit Contact</h1>
      {contact && <ContactForm initialData={contact} onSubmit={handleSubmit} isSubmitting={isSubmitting} />}
    </div>
  );
};

export default EditContact;
