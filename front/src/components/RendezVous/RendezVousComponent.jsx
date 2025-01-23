// src/components/RendezVous/RendezVousComponent.jsx
import React, { useState, useEffect } from 'react';
import axios from 'axios'; // Import axios
import './RendezVousComponent.css'; // Create this file later

const RendezVousComponent = () => {
    const [doctors, setDoctors] = useState([]);
    const [selectedDoctor, setSelectedDoctor] = useState(null);
    const [availableTimes, setAvailableTimes] = useState([]);
    const [selectedTime, setSelectedTime] = useState(null);
    const [patientId, setPatientId] = useState(''); // You'll need to get this dynamically (e.g., from user authentication)
    const [bookingStatus, setBookingStatus] = useState(null); // To display success/error messages

    useEffect(() => {
        // Fetch the list of doctors from your backend API
        const fetchDoctors = async () => {
            try {
                const response = await axios.get('/medecins'); // Adjust the endpoint if necessary
                setDoctors(response.data);
            } catch (error) {
                console.error("Error fetching doctors:", error);
            }
        };

        fetchDoctors();
    }, []);

    const handleDoctorSelect = (doctor) => {
        setSelectedDoctor(doctor);
        // Fetch available times for the selected doctor
        fetchAvailableTimes(doctor.id);
    };

    const fetchAvailableTimes = async (doctorId) => {
        try {
            // Replace with your actual API endpoint to get available times for a doctor
            const response = await axios.get(`/rendezvous/disponibilites?medecinId=${doctorId}`);
            setAvailableTimes(response.data);
        } catch (error) {
            console.error("Error fetching available times:", error);
        }
    };

    const handleTimeSelect = (time) => {
        setSelectedTime(time);
    };

    const handleBooking = async () => {
        if (!selectedDoctor || !selectedTime || !patientId) {
            alert("Please select a doctor, time, and enter your Patient ID.");
            return;
        }

        try {
            // Adjust the endpoint if necessary and format the date/time correctly
            const response = await axios.post('/rendezvous/book', {
                medecinId: selectedDoctor.id,
                patientId: patientId,
                dateTime: selectedTime.debut  // or format selectedTime to match your backend
            });

            if (response.status === 201) {
                setBookingStatus('success');
                alert("Appointment booked successfully!");
            } else {
                setBookingStatus('error');
                alert("Appointment booking failed. Please try again.");
            }
        } catch (error) {
            console.error("Error booking appointment:", error);
            setBookingStatus('error');
            if (error.response && error.response.data) {
                alert(`Error: ${error.response.data}`); // Show the error message from the backend
            } else {
                alert("An error occurred while booking.  Please check the console.");
            }

        }
    };

    return (
        <div className="rendezvous-container">
            <h1>Book Your Appointment</h1>

            <div className="doctor-selection">
                <h2>Select a Doctor:</h2>
                <ul>
                    {doctors.map(doctor => (
                        <li key={doctor.id} onClick={() => handleDoctorSelect(doctor)}>
                            {doctor.nom} - {doctor.specialite}
                        </li>
                    ))}
                </ul>
            </div>

            {selectedDoctor && (
                <div className="time-selection">
                    <h2>Available Times for {selectedDoctor.nom}:</h2>
                    <ul>
                        {availableTimes.map(time => (
                            <li key={time.id} onClick={() => handleTimeSelect(time)}>
                                {time.debut} - {time.fin}
                            </li>
                        ))}
                    </ul>
                </div>
            )}

            {selectedTime && (
                <div className="booking-confirmation">
                    <h2>Confirm Your Booking:</h2>
                    <p>Doctor: {selectedDoctor.nom}</p>
                    <p>Time: {selectedTime.debut} - {selectedTime.fin}</p>
                    <label htmlFor="patientId">Your Patient ID:</label>
                    <input
                        type="text"
                        id="patientId"
                        value={patientId}
                        onChange={(e) => setPatientId(e.target.value)}
                    />
                    <button onClick={handleBooking}>Book Appointment</button>
                </div>
            )}

            {bookingStatus === 'success' && (
                <p className="success-message">Appointment booked successfully!</p>
            )}

            {bookingStatus === 'error' && (
                <p className="error-message">Error booking appointment. Please try again.</p>
            )}
        </div>
    );
};

export default RendezVousComponent;