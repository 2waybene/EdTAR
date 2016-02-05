#!/usr/bin/perl 

my $fileIN = $ARGV[0];
open (IN, $fileIN) || die "Can't open for read in!\n";
while (<IN>)
{
	chomp;
	@ins = split /\t/, $_;
	if ($ins[4] !~ /[0-9]+/)
	{
		die "There was one entry that column 5 was not an integer\n";
	}
}
        
close(IN);
exit(1);



