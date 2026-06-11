import React from 'react';
import { Link } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { Phone, Mail, MapPin, Edit, Trash2 } from 'lucide-react';
import { deleteContact } from '../store/contactsSlice';

const ContactCard = ({ contact }) => {
  const dispatch = useDispatch();

  const handleDelete = () => {
    if (window.confirm('Are you sure you want to delete this contact?')) {
      dispatch(deleteContact(contact.id));
    }
  };

  return (
    <div className="card">
      <div className="flex justify-between items-center mb-4">
        <h3 style={{ fontSize: '1.25rem', fontWeight: '600' }}>
          {contact.firstName} {contact.lastName}
        </h3>
        <div className="flex gap-2">
          <Link to={`/edit/${contact.id}`} className="btn-icon" title="Edit">
            <Edit size={18} />
          </Link>
          <button onClick={handleDelete} className="btn-icon" style={{ color: 'var(--danger)' }} title="Delete">
            <Trash2 size={18} />
          </button>
        </div>
      </div>
      
      <div className="flex flex-col gap-2">
        <div className="flex items-center gap-2" style={{ color: '#7f8c8d' }}>
          <Phone size={16} />
          <span>{contact.phoneNumber}</span>
        </div>
        <div className="flex items-center gap-2" style={{ color: '#7f8c8d' }}>
          <Mail size={16} />
          <span>{contact.email}</span>
        </div>
        <div className="flex items-center gap-2" style={{ color: '#7f8c8d' }}>
          <MapPin size={16} />
          <span>{contact.city}, {contact.state}</span>
        </div>
      </div>
    </div>
  );
};

export default ContactCard;
