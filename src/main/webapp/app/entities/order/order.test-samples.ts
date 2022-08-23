import dayjs from 'dayjs/esm';

import { IOrder, NewOrder } from './order.model';

export const sampleWithRequiredData: IOrder = {
  id: 47761,
  orderDate: dayjs('2022-08-16T02:05'),
  quantity: 70907,
  orderStatus: 'Gourde Texas',
};

export const sampleWithPartialData: IOrder = {
  id: 68170,
  orderDate: dayjs('2022-08-15T11:52'),
  quantity: 61522,
  orderStatus: 'indexing',
};

export const sampleWithFullData: IOrder = {
  id: 44107,
  orderDate: dayjs('2022-08-15T19:35'),
  quantity: 57558,
  orderStatus: 'Cambridgeshire back-end violet',
};

export const sampleWithNewData: NewOrder = {
  orderDate: dayjs('2022-08-16T00:37'),
  quantity: 19671,
  orderStatus: 'International',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
