import React, { useRef, useState, useEffect, } from "react";
import HttpClient from "../lib/HttpClient.tsx";

// Message type
interface Message {
  id: number;
  description: string;
}

const Message: React.FC = () => {
  const [messages, setMessages] = useState<Message[]>([]);
  const [modalOpen, setModalOpen] = useState(false);
  const [currentMessage, setCurrentMessage] = useState<Message | null>(null);
  const [descriptionInput, setDescriptionInput] = useState('');

  // Fetch all messages on component mount
  useEffect(() => {
    fetchMessages();
  }, []);

  const fetchMessages = async () => {
    try {
      const response = await HttpClient.get<Message[]>('/message/all');
      setMessages(response.data);
    } catch (error) {
      console.error('Error fetching messages:', error);
      alert('Failed to fetch messages.');
    }
  };

  const openCreateModal = () => {
    setCurrentMessage(null);
    setDescriptionInput('');
    setModalOpen(true);
  };

  const openEditModal = (message: Message) => {
    setCurrentMessage(message);
    setDescriptionInput(message.description);
    setModalOpen(true);
  };

  const handleSave = async () => {
    if (descriptionInput.trim() === '') return;

    try {
      if (currentMessage) {
        // Update existing message
        await HttpClient.put(`/message/update`, {
          id: currentMessage.id,
          description: descriptionInput,
        });
      } else {
        // Create new message
        await HttpClient.post('/message/create', { description: descriptionInput });
      }

      setModalOpen(false);
      setDescriptionInput('');
      fetchMessages(); // refresh list
    } catch (error) {
      console.error('Error saving message:', error);
      alert('Failed to save message.');
    }
  };

  const handleDelete = async (id: number) => {
  if (!window.confirm('Are you sure you want to delete this message?')) return;

  try {
    // Send ID as request param
    await HttpClient.delete('/message/delete', { params: { id } });
    fetchMessages();
  } catch (error) {
    console.error('Error deleting message:', error);
    alert('Failed to delete message.');
  }
};

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Messages</h1>

      <div className="mb-4">
        <button
          onClick={openCreateModal}
          className="px-4 py-2 bg-blue-500 text-white rounded mr-2"
        >
          Create Message
        </button>
        <button
          onClick={fetchMessages}
          className="px-4 py-2 bg-green-500 text-white rounded"
        >
          Refresh Messages
        </button>
      </div>

      <ul>
        {messages.map((msg) => (
          <li key={msg.id} className="mb-2 flex justify-between items-center border p-2 rounded">
            <span>
              <strong>ID:</strong> {msg.id} | <strong>Description:</strong> {msg.description}
            </span>
            <div>
              <button
                onClick={() => openEditModal(msg)}
                className="px-2 py-1 bg-yellow-400 rounded mr-2"
              >
                Modify
              </button>
              <button
                onClick={() => handleDelete(msg.id)}
                className="px-2 py-1 bg-red-500 text-white rounded"
              >
                Delete
              </button>
            </div>
          </li>
        ))}
      </ul>

      {/* Modal */}
      {modalOpen && (
        <div className="fixed inset-0 bg-black bg-opacity-40 flex justify-center items-center">
          <div className="bg-white p-6 rounded shadow w-96">
            <h2 className="text-xl font-bold mb-4">
              {currentMessage ? 'Edit Message' : 'Create Message'}
            </h2>
            <input
              type="text"
              placeholder="Description"
              value={descriptionInput}
              onChange={(e) => setDescriptionInput(e.target.value)}
              className="w-full border p-2 rounded mb-4"
            />
            <div className="flex justify-end">
              <button
                onClick={() => setModalOpen(false)}
                className="px-4 py-2 bg-gray-300 rounded mr-2"
              >
                Cancel
              </button>
              <button
                onClick={handleSave}
                className="px-4 py-2 bg-blue-500 text-white rounded"
              >
                Save
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Message;
