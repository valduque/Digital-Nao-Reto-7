// Minimal API client for the backend reservations module.
// Kept simple & modular so you can mock/fake it in Jest tests.

const BASE_URL = "http://localhost:8080/api/reservations";

export async function listReservations() {
  const res = await fetch(BASE_URL);
  if (!res.ok) throw new Error("Failed to fetch reservations");
  return res.json();
}

export async function createReservation(payload) {
  const res = await fetch(BASE_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload)
  });
  if (!res.ok) throw new Error((await res.json()).message || "Create failed");
  return res.json();
}

export async function updateReservation(id, payload) {
  const res = await fetch(`${BASE_URL}/${encodeURIComponent(id)}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload)
  });
  if (!res.ok) throw new Error((await res.json()).message || "Update failed");
  return res.json();
}

export async function cancelReservation(id) {
  const res = await fetch(`${BASE_URL}/${encodeURIComponent(id)}`, { method: "DELETE" });
  if (!res.ok) throw new Error((await res.json()).message || "Cancel failed");
  return res.json();
}
