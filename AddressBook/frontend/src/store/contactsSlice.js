import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';

const API_URL = 'http://localhost:3000/contacts';

// Thunks for API interaction
export const fetchContacts = createAsyncThunk('contacts/fetchContacts', async () => {
  const response = await fetch(API_URL);
  if (!response.ok) throw new Error('Failed to fetch contacts');
  return response.json();
});

export const addContact = createAsyncThunk('contacts/addContact', async (newContact) => {
  const response = await fetch(API_URL, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(newContact),
  });
  if (!response.ok) throw new Error('Failed to add contact');
  return response.json();
});

export const updateContact = createAsyncThunk('contacts/updateContact', async (contact) => {
  const response = await fetch(`${API_URL}/${contact.id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(contact),
  });
  if (!response.ok) throw new Error('Failed to update contact');
  return response.json();
});

export const deleteContact = createAsyncThunk('contacts/deleteContact', async (id) => {
  const response = await fetch(`${API_URL}/${id}`, {
    method: 'DELETE',
  });
  if (!response.ok) throw new Error('Failed to delete contact');
  return id;
});

const contactsSlice = createSlice({
  name: 'contacts',
  initialState: {
    items: [],
    status: 'idle', // 'idle' | 'loading' | 'succeeded' | 'failed'
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      // Fetch
      .addCase(fetchContacts.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchContacts.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.items = action.payload;
      })
      .addCase(fetchContacts.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error.message;
      })
      // Add
      .addCase(addContact.fulfilled, (state, action) => {
        state.items.push(action.payload);
      })
      // Update
      .addCase(updateContact.fulfilled, (state, action) => {
        const index = state.items.findIndex(c => c.id === action.payload.id);
        if (index !== -1) {
          state.items[index] = action.payload;
        }
      })
      // Delete
      .addCase(deleteContact.fulfilled, (state, action) => {
        state.items = state.items.filter(c => c.id !== action.payload);
      });
  },
});

export default contactsSlice.reducer;
