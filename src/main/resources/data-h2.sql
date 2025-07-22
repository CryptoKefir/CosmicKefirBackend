INSERT INTO wallet (user_id, balance, currency, version) VALUES
  (0, 100.00, 'BTC', 0),
  (1, 1000.00, 'USD', 0),
  (2, 250.50, 'EUR', 0);

INSERT INTO user(name, email, wallet_id) VALUES
('Leeam', 'ooga@booga.com', 0);