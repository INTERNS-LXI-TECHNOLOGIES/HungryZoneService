import { ICartItem, NewCartItem } from './cart-item.model';

export const sampleWithRequiredData: ICartItem = {
  id: 44067,
  quantity: 2440,
};

export const sampleWithPartialData: ICartItem = {
  id: 44748,
  quantity: 53483,
};

export const sampleWithFullData: ICartItem = {
  id: 23921,
  quantity: 26751,
};

export const sampleWithNewData: NewCartItem = {
  quantity: 78154,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
