#!/usr/bin/perl 

my $fileIN = $ARGV[0];
open (IN, $fileIN) || die "Can't open for read in!\n";
while (<IN>)
{
	chomp;
	@ins = split /\t/, $_;
	if ($#ins  !=  6 )
	{
		die "There was one entry that does not have 7 columns \n";
	}
}
        
close(IN);
exit(1);



