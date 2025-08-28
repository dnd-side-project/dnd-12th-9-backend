redis.call('mset', KEYS[1], ARGV[1], KEYS[2], ARGV[2]);
redis.call('pexpire', KEYS[1], ARGV[3]);
redis.call('pexpire', KEYS[2], ARGV[3]);
