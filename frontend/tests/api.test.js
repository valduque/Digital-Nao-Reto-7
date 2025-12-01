import {
  listReservations,
  createReservation,
  updateReservation,
  cancelReservation
} from "../js/api.js";

import { jest } from "@jest/globals";

global.fetch = jest.fn();


describe("API — listReservations", () => {
  test("returns reservations successfully", async () => {
    const mockData = [{ id: 1, name: "Test" }];

    fetch.mockResolvedValueOnce({
      ok: true,
      json: () => Promise.resolve(mockData)
    });

    const result = await listReservations();
    expect(result).toEqual(mockData);
  });

  test("throws error when response is not ok", async () => {
    fetch.mockResolvedValueOnce({ ok: false });

    await expect(listReservations()).rejects.toThrow("Failed to list reservations");
  });
});

describe("API — createReservation", () => {
  test("creates reservation successfully", async () => {
    const mockResponse = { id: 1 };

    fetch.mockResolvedValueOnce({
      ok: true,
      json: () => Promise.resolve(mockResponse)
    });

    const data = { name: "Valeria" };
    const result = await createReservation(data);

    expect(result).toEqual(mockResponse);
  });

  test("throws error when creation fails", async () => {
    fetch.mockResolvedValueOnce({ ok: false });

    await expect(createReservation({})).rejects.toThrow("Failed to create reservation");
  });
});

describe("API — updateReservation", () => {
  test("updates reservation successfully", async () => {
    const mockResponse = { updated: true };

    fetch.mockResolvedValueOnce({
      ok: true,
      json: () => Promise.resolve(mockResponse)
    });

    const result = await updateReservation(1, { name: "Updated" });
    expect(result).toEqual(mockResponse);
  });

  test("throws error when update fails", async () => {
    fetch.mockResolvedValueOnce({ ok: false });

    await expect(updateReservation(1, {})).rejects.toThrow("Failed to update reservation");
  });
});

describe("API — cancelReservation", () => {
  test("cancels reservation successfully", async () => {
    const mockResponse = { deleted: true };

    fetch.mockResolvedValueOnce({
      ok: true,
      json: () => Promise.resolve(mockResponse)
    });

    const result = await cancelReservation(1);
    expect(result).toEqual(mockResponse);
  });

  test("throws error when cancel fails", () => {
    fetch.mockResolvedValueOnce({ ok: false });

    return expect(cancelReservation(1)).rejects.toThrow("Failed to cancel reservation");
  });
});
