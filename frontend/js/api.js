export async function listReservations() {
  const res = await fetch("http://localhost:8080/reservations");
  if (!res.ok) throw new Error("Failed to list reservations");
  return res.json();
}

export async function createReservation(data) {
  const res = await fetch("http://localhost:8080/reservations", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  });
  if (!res.ok) throw new Error("Failed to create reservation");
  return res.json();
}

export async function updateReservation(id, data) {
  const res = await fetch(`http://localhost:8080/reservations/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  });
  if (!res.ok) throw new Error("Failed to update reservation");
  return res.json();
}

export async function cancelReservation(id) {
  const res = await fetch(`http://localhost:8080/reservations/${id}`, {
    method: "DELETE"
  });
  if (!res.ok) throw new Error("Failed to cancel reservation");
  return res.json();
}
