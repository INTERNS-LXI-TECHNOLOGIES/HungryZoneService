import dayjs from 'dayjs/esm';

import { IOrder, NewOrder } from './order.model';

export const sampleWithRequiredData: IOrder = {
  id: 47761,
  orderDate: dayjs('2022-08-16T02:05'),
  quantity: 70907,
  unit: 'Gourde Texas',
  orderStatus: 'Communications indexing e-commerce',
};

export const sampleWithPartialData: IOrder = {
  id: 96762,
  orderDate: dayjs('2022-08-15T22:33'),
  quantity: 96461,
  unit: 'back-end',
  orderStatus: 'markets',
};

export const sampleWithFullData: IOrder = {
  id: 19671,
  orderDate: dayjs('2022-08-16T02:39'),
  quantity: 92419,
  unit: 'Indiana Unbranded 24/7',
  orderStatus: 'Mississippi Walk overriding',
};

export const sampleWithNewData: NewOrder = {
  orderDate: dayjs('2022-08-15T12:57'),
  quantity: 19115,
  unit: 'Ville benchmark',
  orderStatus: 'vertical Identity Rubber',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
